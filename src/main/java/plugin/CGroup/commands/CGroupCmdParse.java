package plugin.CGroup.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.CGroup.managers.ChannelManager;
import plugin.CGroup.managers.GroupManager;
import plugin.CGroup.managers.InviteManager;

public class CGroupCmdParse implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c(Error)§f You must be a player to use this command");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("cgroup.use")) {
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§c(Error)§f No arguments passed\n\n§3(Info)§f Do §7/cgroup help§f for a list of commands and syntax");
            return true;
        }

        sender.sendMessage(switch(args[0]){
            case "invite" -> InviteManager.parseInvite(player, args);
            case "group" -> GroupManager.parseGroup(player, args);
            case "channel" -> ChannelManager.switchChannel(player, args);
            case "help" -> helpParse(args);
            default -> "§c(Error)§f Invalid argument: " + args[0];
        });
        return true;
    }

    private String helpParse(String[] args) {
        switch (args.length) {
            case 1: return "§e(Help) §e§lCGroup V" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() + "\n§e(Help)§f /cgroup [group | invite | channel]§f" + "\n§e(Help)§f Use §7/cgroup help [group| invite | channel]§f to get more info";
            case 2: {
                return switch (args[1]) {
                    case "group" -> "§b----------------------- §e(Help | Group)§b -----------------------" +
                                "\n>§f §esend <alias> <invitee>§f" +
                                "\n   > §e<alias>§f   | Alias of group to send invite to" +
                                "\n   > §e<invitee>§f | Name of player to send invite to" +
                                "\n> §erevoke <alias> <invitee>§f" +
                                "\n   > §e<alias>§f   | Alias of group to revoke invite to" +
                                "\n   > §e<invitee>§f | Name of player to revoke invite to" +
                                "\n> §eaccept <alias>§f" +
                                "\n   > §e<alias>§f   | Alias of group to accept invite to" +
                                "\n> §ereject <alias>§f" +
                                "\n   > §e<alias>§f   | Alias of group to reject invite from" +
                                "\n§b----------------------------------------------------------§f";
                    case "invite" -> "   §b----------------------- §e(Help | Invite)§b -----------------------§f" +
                                "\n> §ecreate <name> <alias> <colour>§f" +
                                "\n   > §e<name>§f    | Name of new group" +
                                "\n   > §e<alias>§f   | Alias of new group" +
                                "\n   > §e<colour>§f  | Colour the group will show up in" +
                                "\n> §edisband <alias>§f" +
                                "\n   > §e<alias>§f   | Alias of group to disband" +
                                "\n> §eleave <alias>§f" +
                                "\n   > §e<alias>§f   | Alias of group to leave" +
                                "\n> §eremove <alias> <member>§f" +
                                "\n   > §e<alias>§f   | Alias of group to remove member from" +
                                "\n   > §e<member>§f  | Name of member to remove from group" +
                                "\n§b----------------------------------------------------------§f";
                    case "channel" -> "§b---------------------- §e(Help | Channel)§b ----------------------§f" +
                                "\n> §fchannel <alias>" +
                                "\n   > §e<alias>§f   | Alias of group to start speaking in. If group with given alias does not exist, you will be sent to §eGlobal§f chat" +
                                "\n§b----------------------------------------------------------§f";
                    default -> "§e(Help) §eCGroup V" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() +
                            "\n§e(Help)§f /cgroup [group | invite | channel]§f" +
                            "\n§e(Help)§f Use §7/cgroup help [group| invite | channel]§f to get more info";
                };
            }
            default: return "§c(Error)§f Too many arguments";
        }
    }
}
