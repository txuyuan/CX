// 
// Decompiled by Procyon v0.5.36
// 

package plugin.CGroup;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CGroupCmdParse implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c(Error)§f You must be a player to use this command");
            return true;
        }
        Player player = (Player)sender;
        if (!player.hasPermission("cgroup.use")) {
            sender.sendMessage("§c(Error)§f You do not have permission to use CGroup");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§c(Error)§f No arguments passed\n\n§3(Info)§f Do §7/cgroup help§f for a list of commands and syntax");
            return true;
        }
        String s;
        switch (s = args[0]) {
            case "invite": {
                sender.sendMessage(InviteManager.parseInvite(player, args));
                return true;
            }
            case "help": {
                sender.sendMessage(this.helpParse(args));
                return true;
            }
            case "group": {
                sender.sendMessage(GroupManager.parseGroup(player, args));
                return true;
            }
            case "channel": {
                sender.sendMessage(ChannelManager.switchChannel(player, args));
                return true;
            }
            default:
                break;
        }
        sender.sendMessage("§c(Error)§f \"" + args[0] + "\" is not a valid argument" + "\n§3(Info)§f Do §7/cgroup help§f for a list of commands and syntax");
        return true;
    }
    
    private String helpParse(String[] args) {
        switch (args.length) {
            case 1: 
                return "§e(Help) §eCGroup V" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() + "\n§e(Help)§f /cgroup [group | invite | channel]§f" + "\n§e(Help)§f Use §7/cgroup help [group| invite | channel]§f to get more info";
            case 2: {
                String s;
                switch (s = args[1]) {
                    case "invite": 
                        return "§9----§f §e(Help | Group)§9 ----\n>§f §esend <alias> <invitee>§f\n   > §e<alias>§f   | Alias of group to send invite to\n   > §e<invitee>§f | Name of player to send invite to\n> §erevoke <alias> <invitee>§f\n   > §e<alias>§f   | Alias of group to revoke invite to\n   > §e<invitee>§f | Name of player to revoke invite to\n> §eaccept <alias>§f\n   > §e<alias>§f   | Alias of group to accept invite to\n> §ereject <alias>§f\n   > §e<alias>§f   | Alias of group to reject invite from\n§9-------------------------------§f";
                    case "group": 
                        return "§9----§f §e(Help | Invite)§9 ----§f\n> §ecreate <name> <alias> <colour>§f\n   > §e<name>§f    | Name of new group\n   > §e<alias>§f   | Alias of new group\n   > §e<colour>§f  | Colour the group will show up in\n> §edisband <alias>§f\n   > §e<alias>§f   | Alias of group to disband\n> §eleave <alias>§f\n   > §e<alias>§f   | Alias of group to leave\n> §eremove <alias> <member>§f\n   > §e<alias>§f   | Alias of group to remove member from\n   > §e<member>§f  | Name of member to remove from group\n§9---------------------------§f";
                    case "channel": 
                        return "§9----§f §e(Help | Channel)§9 ----§f\n> §fchannel <alias>\n   > §e<alias>§f   | Alias of group to start speaking in. If group with given alias does not exist, you will be sent to §eGlobal§f chat\n§9------------------------------§9";
                    default:
                        break;
                }
                return "§e(Help) §eCGroup V" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() + "\n§e(Help)§f /cgroup [group | invite | channel]§f" + "\n§e(Help)§f Use §7/cgroup help [group| invite | channel]§f to get more info";
            }
            default: 
                return "§c(Error)§f Too many arguments";
        }
    }
}
