package plugin.CGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import plugin.CX.Main;
import java.util.logging.Level;

public class Completer implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions;
		
		if (!command.getName().equalsIgnoreCase("cgroup") || !(sender instanceof Player)) {
			Main.getInstance().getLogger().log(Level.INFO, "test 1");
			return Arrays.asList("");
		}
		
		Player player = (Player)sender;
		String playerUUID = player.getUniqueId().toString();
		FileConfiguration data = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml"));
		
		if (args.length == 1) 
			completions = Arrays.asList("channel", "group", "help", "invite");
		else {
            switch (args[0]) {
                case "channel": {
                    completions = this.channelComplete(args, playerUUID, data);
                    break;
                }
                case "group": {
                    completions = this.groupComplete(args, playerUUID, data);
                    break;
                }
                case "invite": {
                    completions = this.inviteComplete(args, playerUUID, data);
                    break;
                }
                case "help": {
                    completions = Arrays.asList("group", "invite", "channel");
                    break;
                }
                default: {
                    completions = Arrays.asList("");
                    break;
                }
            }   
        }

        return completions.stream().filter(argument -> argument.indexOf(args[args.length - 1]) == 0).sorted().collect(Collectors.toList());
    }
    
    

    private List<String> helpComplete() {
        List<String> completions = Arrays.asList("group", "invite", "channel");
        return completions;
    }

    private List<String> channelComplete(String[] args, String player, FileConfiguration data) {
        List<String> completions;
        if (args.length == 2) {
            if (data.getConfigurationSection("groups") != null) {
                completions = data.getConfigurationSection("groups").getKeys(false).stream().map(key -> data.getObject("groups." + key, Group.class)).map(group -> group.getAlias()).collect(Collectors.toList());
                completions.add("ALL");
            } else 
                completions = Arrays.asList("ALL");
        } else 
            completions = Arrays.asList("");
        return completions;
    }

    private List<String> groupComplete(String[] args, String player, FileConfiguration data) {
        List<String> completions;
        if (args.length == 2) {
            completions = Arrays.asList("create", "disband", "leave", "remove", "transfer", "info");
        } else if (args.length == 3) {
            if (args[1].equals("disband") || args[1].equals("remove") || args[1].equals("transfer")) 
                completions = data.getConfigurationSection("groups") != null ? data.getConfigurationSection("groups").getKeys(false).stream().map(key -> (Group)data.getObject("groups." + key, Group.class)).filter(group -> group.ownedBy(player)).map(group -> group.getAlias()).collect(Collectors.toList()) : Arrays.asList(new String[0]);
            else if (args[1].equals("leave")) 
                completions = data.getConfigurationSection("groups") != null ? data.getConfigurationSection("groups").getKeys(false).stream().map(key -> (Group)data.getObject("groups." + key, Group.class)).filter(group -> group.getMembers().contains(player)).filter(group -> group.ownedBy(player) == false).map(group -> group.getAlias()).collect(Collectors.toList()) : Arrays.asList(new String[0]);
            else if (args[1].equals("info")) {
            	completions = data.getConfigurationSection("groups").getKeys(false).stream().map(key -> data.getObject("groups." + key, Group.class)).map(group -> group.getAlias()).collect(Collectors.toList());
                completions.add("ALL");
            } else 
                completions = Arrays.asList("");
        } else {
            Group group2;
            completions = args.length == 4 && (args[1].equals("remove") || args[1].equals("transfer")) ? ((group2 = data.getObject("groups." + args[2], Group.class)) != null && group2.ownedBy(player).booleanValue() ? (data.getConfigurationSection("groups") != null ? group2.getMembers().stream().filter(member -> !member.equals(group2.getOwner())).map(UUID::fromString).map(Bukkit::getOfflinePlayer).map(member -> member.getName()).collect(Collectors.toList()) : Arrays.asList(new String[0])) : Arrays.asList(new String[0])) : (args.length == 5 && args[1].equals("create") ? Arrays.asList("black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white") : Arrays.asList(new String[0]));
        }
        return completions;
    }

    private List<String> inviteComplete(String[] args, String player, FileConfiguration data) {
        Group group2;
        List<String> completions = args.length == 2 ? Arrays.asList("accept", "reject", "revoke", "send") : (args.length == 3 ? (args[1].equals("revoke") || args[1].equals("send") ? (data.getConfigurationSection("groups") != null ? 
        		data.getConfigurationSection("groups").getKeys(false).stream().map(key -> data.getObject("groups." + key, Group.class)).filter
        		(group -> group.ownedBy(player)).map(group -> group.getAlias()).collect(Collectors.toList()) : Arrays.asList(new String[0])) : (args[1].equals("accept") || args[1].equals("reject") ? (data.getConfigurationSection("groups") != null ? data.getConfigurationSection("groups").getKeys(false).stream().map(key -> (Group)data.getObject("groups." + key, Group.class)).filter(group -> group.getInvites().contains(player)).map(group -> group.getAlias()).collect(Collectors.toList()) : Arrays.asList(new String[0])) : Arrays.asList(new String[0]))) : (args.length == 4 ? ((group2 = (Group)data.getObject("groups." + args[2], Group.class)) != null && group2.ownedBy(player).booleanValue() ? (args[1].equals("send") ? (data.getConfigurationSection("groups") != null ? Bukkit.getOnlinePlayers().stream().filter(onlinePlayer -> !onlinePlayer.getUniqueId().toString().equals(player) && !group2.getInvites().contains(onlinePlayer.getUniqueId().toString()) && !group2.getMembers().contains(onlinePlayer.getUniqueId().toString())).map(onlinePlayer -> onlinePlayer.getName()).collect(Collectors.toList()) : Arrays.asList(new String[0])) : (args[1].equals("revoke") ? (data.getConfigurationSection("groups") != null ? group2.getInvites().stream().map(UUID::fromString).map(Bukkit::getOfflinePlayer).map(invitedPlayer -> invitedPlayer.getName()).collect(Collectors.toList()) : Arrays.asList(new String[0])) : Arrays.asList(new String[0]))) : Arrays.asList(new String[0])) : Arrays.asList(new String[0])));
        return completions;
    }
}

