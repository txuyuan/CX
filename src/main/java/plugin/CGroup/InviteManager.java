package plugin.CGroup;

import org.bukkit.configuration.file.FileConfiguration;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import plugin.CX.Main;
import java.util.logging.Level;

public class InviteManager
{
    public static String parseInvite(Player player, String[] args) {
        if (args.length < 2) {
            return "§c(Error)§f Please specify an action\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
        String s;
        switch (s = args[1]) {
            case "accept": 
                return acceptInvite(player, args);
            case "reject": 
                return rejectInvite(player, args);
            case "revoke": 
                return revokeInvite(player, args);
            case "send": 
                return sendInvite(player, args);
            default:
                break;
        }
        return "§c(Error)§f \"" + args[1] + "\" is not a valid argument" + "\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
    }
    
    private static String sendInvite(Player player, String[] args) {
        switch (args.length) {
            case 2: 
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3: 
                return "§c(Error)§f Please specify a player\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 4: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (group == null) 
                    return "§c(Error)§f A group with alias §f§n§o" + args[2].toUpperCase() + "§c does not exist";
                if (!group.ownedBy(player.getUniqueId().toString())) 
                    return "§c(Error)§f You are not the owner of " + group.getFormattedName();
                String invitedPlayerName = args[3];
                
                
                switch (group.sendInvite(invitedPlayerName)) {
                    case 1: 
                        return "§c(Error)§f " + invitedPlayerName + " §chas not played on this server before";
                    case 2: 
                        return "§c(Error)§f " + invitedPlayerName + " §cis a member of " + group.getFormattedName();
                    case 3: 
                        return "§c(Error)§f " + invitedPlayerName + " §chas an invite to " + group.getFormattedName();
                    default: {
                    	Player invitee = Bukkit.getPlayer(invitedPlayerName);
                    	if (invitee != null) 
                    		invitee.sendMessage("CGROUP | §dINFO§f >> You have been invited to join " + group.getFormattedName() + " by §e" + player.getDisplayName()
                    		+ "\n§fCGROUP | §dINFO§f >> Use §7/cgroup invite accept " + group.getAlias() + "§f to accept invitation");
                        data.set("groups." + group.getAlias(), group);
                        try {
                            data.save(dataFile);}
                        catch (IOException exception) {
                            exception.printStackTrace();
                            Main.getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Failed to write to disk");
                            return "§c(Error)§f Failed to write to disk";
                        }
                        return "§b(Status)§f You sent an invite to " + group.getFormattedName() + " to §e" + invitedPlayerName;
                    }
                }
            }
            
            default: 
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }
    
    private static String revokeInvite(Player player, String[] args) {
        switch (args.length) {
            case 2: 
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3: 
                return "§c(Error)§f Please specify a player\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 4: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (group == null) 
                    return "§c(Error)§f A group with alias §f§n§o" + args[2].toUpperCase() + "§c does not exist";
                if (!group.ownedBy(player.getUniqueId().toString())) 
                    return "§c(Error)§f You are not the owner of " + group.getFormattedName();
                String revokedPlayerName = args[3];
                if (!group.revokeInvite(revokedPlayerName)) 
                    return "§c(Error)§f " + revokedPlayerName + " §cdoes not have an invite to " + group.getFormattedName();
                data.set("groups." + group.getAlias(), group);
                try {
                    data.save(dataFile);}
                catch (IOException exception) {
                    exception.printStackTrace();
                    Main.getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Failed to write to disk");
                    return "§c(Error)§f Failed to write to disk";
                }
                return "§b(Status)§f Revoked §e" + revokedPlayerName + "'s to " + group.getFormattedName();
            }
            default: 
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }
    
    private static String acceptInvite(Player player, String[] args) {
        switch (args.length) {
            case 2: 
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (group == null) 
                    return "§c(Error)§f A group with alias §f§n§o" + args[2].toUpperCase() + "§c does not exist";
                if (!group.acceptInvite(player.getUniqueId().toString())) 
                    return "§c(Error)§f You do not have an invite to " + group.getFormattedName();
                data.set("groups." + group.getAlias(), group);
                try {
                    data.save(dataFile);}
                catch (IOException exception) {
                    exception.printStackTrace();
                    Main.getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Failed to write to disk");
                    return "§c(Error)§f Failed to write to disk";
                }
                return "§b(Status)§f Accepted your invite to " + group.getFormattedName();
            }
            default: 
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }
    
    private static String rejectInvite(Player player, String[] args) {
        switch (args.length) {
            case 2: 
                return "§c(Error)§f Please specify a group\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
            case 3: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                Group group = Group.getGroup(args[2], data);
                if (group == null) 
                    return "§c(Error)§f A group with alias §f§n§o" + args[2].toUpperCase() + "§c does not exist";
                if (!group.rejectInvite(player.getUniqueId().toString())) 
                    return "§c(Error)§f You do not have an invite to " + group.getFormattedName();
                data.set("groups." + group.getAlias(), group);
                try {
                    data.save(dataFile);}
                catch (IOException exception) {
                    exception.printStackTrace();
                    Main.getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Failed to write to disk");
                    return "§c(Error)§f Failed to write to disk";
                }
                return "§b(Status)§f Rejected your invite to " + group.getFormattedName();
            }
            default: 
                return "§c(Error)§f Too many arguments specified\n§e(Help)§f Do §7/cgroup help §ffor a list of commands and syntax";
        }
    }
}
