package plugin.CGroup;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import plugin.CX.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GroupManager {
    public static String parseGroup(Player sender, String[] args) {
        if (args.length < 2)
            return "§c(Error)§f Please specify an action\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        String s;
        switch (s = args[1]) {
            case "create":
                return createGroup(sender, args);
            case "remove":
                return removeMember(sender, args);
            case "info":
                return groupInfo(args);
            case "leave":
                return leaveGroup(sender, args);
            case "transfer":
                return transferOwnership(sender, args);
            case "disband":
                return disbandGroup(sender, args);
            default:
                break;
        }
        return "§c(Error)§f \"" + args[1] + "\" is not a valid argument" + "\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
    }

    private static String groupInfo(String[] args) {
        switch (args.length) {
            case 2:
                return "§c(Error)§f Please specify a group (by alias)\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
            case 3: {
                FileConfiguration data = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml"));
                Group group = Group.getGroup(args[2], data);
                UUID ownerUUID = UUID.fromString(group.getOwner());
                OfflinePlayer offlineOwner = Bukkit.getOfflinePlayer(ownerUUID);
                String info = "------ §3(Info | " + group.getFormattedAlias() + "§3)§f ------" + "\n§aName§f   >> §e" + group.getFormattedName() + "\n§aColour§f >> §" + group.getFormatColour() + group.getColour().toUpperCase().replace("_", " ") + "§f" + "\n§aOwner§f  >> §e" + offlineOwner.getName() + "§f" + "\n§aMembers§f: ";
                ArrayList<String> members = group.getMembers();
                if (members.size() > 0) {
                    for (int i = 0; i < members.size(); ++i) {
                        UUID memberUUID = UUID.fromString(members.get(i));
                        OfflinePlayer offlineMember = Bukkit.getOfflinePlayer(memberUUID);
                        info = info + "\n> §e" + offlineMember.getName() + "§f";
                    }
                } else
                    info = info + "\n> §cNONE§f";
                info = info + "\n§aInvites§f: ";
                ArrayList<String> invites = group.getInvites();
                if (invites.size() > 0) {
                    for (int j = 0; j < invites.size(); ++j) {
                        UUID inviteeUUID = UUID.fromString(members.get(j));
                        OfflinePlayer offlineInvitee = Bukkit.getOfflinePlayer(inviteeUUID);
                        info = info + "\n> §e" + offlineInvitee.getName() + "§f";
                    }
                } else
                    info = info + "\n> §cNONE§f";
                info = info + "\n§f-----------------------------";
                return info;
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
        }
    }

    private static String createGroup(Player sender, String[] args) {
        switch (args.length) {
            case 2:
                return "§c(Error)§f Please specify the group name\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
            case 3:
                return "§c(Error)§f Please specify an alias\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
            case 4:
                return "§c(Error)§f Please choose a group colour\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
            case 5: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                String uuid = sender.getUniqueId().toString();
                ArrayList<String> placeholder = new ArrayList<String>();
                ArrayList<String> initialMembers = new ArrayList<String>();
                initialMembers.add(uuid);
                Group group = new Group("placeholder", "placeholder", "placeholder", uuid, initialMembers, placeholder);
                if (!group.setName(args[2], data))
                    return "§c(Error)§f The group name §f§n" + args[2] + "§c has been taken";
                if (args[2].contains("`"))
                    return "§c(Error)§f The character §f§n`§c is reserved";
                switch (group.setAlias(args[3], data)) {
                    case 1:
                        return "§c(Error)§f The alias must be 3 characters long";
                    case 2:
                        return "§c(Error)§f The alias §e" + args[3] + "§c has been taken";
                    case 3:
                        return "§c(Error)§f The alias §n§oALL §cis reserved for global chat";
                    default: {
                        if (!group.setColour(args[4]))
                            return "§c(Error)§f The colour §f" + args[4] + " is invalid";
                        data.set("groups." + group.getAlias(), group);
                        try {
                            data.save(dataFile);
                        } catch (IOException exception) {
                            Main.logDiskError(exception);
                            return "§c(Error)§f Error writing to disk";
                        }
                        return "§b(Status) You successfully created a group\n§e(Help)§f Name §f>> " + group.getFormattedName() + "\n§e(Help)§f Alias §f>> " + group.getFormattedAlias();
                    }
                }
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help§f for a list of commands and syntax";
        }
    }

    private static String disbandGroup(Player sender, String[] args) {
        switch (args.length) {
            case 2:
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                String grpName = group.getFormattedName();
                String grpOwner = group.getOwner();
                List<String> members = group.getMembers();
                if (group == null)
                    return "§c(Error)§f A group with alias §e" + args[2].toUpperCase() + "§c does not exist";
                if (!group.ownedBy(sender.getUniqueId().toString()))
                    return "§c(Error)§f You are not the owner of " + group.getFormattedName();
                data.set("groups." + group.getAlias(), null);
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    Main.logDiskError(exception);
                    return "§c(Error)§f Error writing to disk";
                }

                for (String playerUUID : members) {
                    if (!(playerUUID.equalsIgnoreCase(grpOwner))) {
                        List<String> disList;
                        if (data.getList("players." + playerUUID + ".disList") != null) {
                            disList = (List<String>) data.getList("players." + playerUUID + ".disList");
                            disList.add(grpName);
                        } else
                            disList = Arrays.asList(grpName);
                        String search = "players." + playerUUID + ".disList";
                        data.set(search, disList);
                        try {
                            data.save(dataFile);
                        } catch (IOException exception) {
                            Main.logDiskError(exception);
                            return "§c(Error)§f Error writing to disk";
                        }
                    }
                }

                return "§b(Status) You disbanded " + group.getFormattedName() + ", affecting §6" + group.getMembers().size() + " player(s)";
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }

    private static String leaveGroup(Player sender, String[] args) {
        switch (args.length) {
            case 2:
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (group.ownedBy(sender.getUniqueId().toString()))
                    return "§c(Error)§f You are the owner of " + group.getFormattedName() + "\n§e(Help)§f You must disband the group or transfer ownership before leaving";
                if (!group.removeMember(sender.getName()))
                    return "§c(Error)§f You are not a member of §e" + group.getFormattedName();
                data.set("groups." + group.getAlias(), group);
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    Main.logDiskError(exception);
                    return "§c(Error)§f Error writing to disk";
                }

                for (String playerUUID : group.getMembers()) {
                    List<String> leaveList;
                    String dats = sender.getDisplayName() + "`" + group.getFormattedName();
                    if (data.getList("players." + playerUUID + ".leaveList") != null) {
                        leaveList = (List<String>) data.getList("players." + playerUUID + ".leaveList");
                        leaveList.add(dats);
                    } else
                        leaveList = Arrays.asList(dats);
                    try {
                        data.save(dataFile);
                    } catch (IOException exception) {
                        Main.logDiskError(exception);
                        return "§c(Error)§f Error writing to disk";
                    }
                }

                return "§b(Status) You left " + group.getFormattedName();
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }

    private static String removeMember(Player sender, String[] args) {
        switch (args.length) {
            case 2:
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3:
                return "§c(Error)§f Please specify a player\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 4: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (!group.ownedBy(sender.getUniqueId().toString()))
                    return "§c(Error)§f You are not the owner of " + group.getFormattedName();
                String removedMemberName = args[3];
                if (!group.removeMember(removedMemberName))
                    return "CGROUP | §aERROR§f >> §e" + removedMemberName + " §cis not a member of " + group.getFormattedName();
                data.set("groups." + group.getAlias(), group);
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    Main.logDiskError(exception);
                    return "§c(Error)§f Error writing to disk";
                }
                for (String playerUUID : group.getMembers()) {
                    if (!(playerUUID == sender.getUniqueId().toString())) {
                        List<String> leaveList;
                        String dats = removedMemberName + "`" + group.getFormattedName();
                        if (data.getList("players." + playerUUID + ".leaveList") != null) {
                            leaveList = (List<String>) data.getList("players." + playerUUID + ".leaveList");
                            leaveList.add(dats);
                        } else
                            leaveList = Arrays.asList(dats);
                        data.set("players." + playerUUID + ".leaveList", leaveList);
                        try {
                            data.save(dataFile);
                        } catch (IOException exception) {
                            Main.logDiskError(exception);
                            return "§c(Error)§f Error writing to disk";
                        }
                    }
                }
                return "§b(Status) You removed §e" + removedMemberName + " from " + group.getFormattedName();
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }

    private static String transferOwnership(Player sender, String[] args) {
        switch (args.length) {
            case 2:
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3:
                return "§c(Error)§f Please specify a player\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 4: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (!group.ownedBy(sender.getUniqueId().toString()))
                    return "§c(Error)§f You are not the owner of " + group.getFormattedName();
                String transferredOwnerName = args[3];
                String transferredOwner = Bukkit.getOfflinePlayer(transferredOwnerName).getUniqueId().toString();
                if (!group.getMembers().contains(transferredOwner))
                    return "CGROUP | §aERROR§f >> §e" + transferredOwnerName + " §c is not a member of " + group.getFormattedName();
                group.setOwner(transferredOwner);
                data.set("groups." + group.getAlias(), group);
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    Main.logDiskError(exception);
                    return "§c(Error)§f Error writing to disk";
                }

                for (String playerUUID : group.getMembers()) {
                    if (playerUUID == sender.getUniqueId().toString())
                        break;
                    List<String> transList;
                    String dats = transferredOwnerName + "`" + group.getFormattedName();
                    if (data.getList("players." + playerUUID + ".transList") != null) {
                        transList = (List<String>) data.getList("players." + playerUUID + ".transList");
                        transList.add(dats);
                    } else
                        transList = Arrays.asList(dats);
                    try {
                        data.save(dataFile);
                    } catch (IOException exception) {
                        Main.logDiskError(exception);
                        return "§c(Error)§f Error writing to disk";
                    }
                }

                return "§b(Status) You tranferred ownership of §e" + group.getFormattedName() + " to §e" + transferredOwnerName;
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }
}
