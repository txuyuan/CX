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
            case 1:{
                return "§e(Help) §e§lCGroup v" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() +
                        "\n> §egroup§f" +
                        "\n> §einvite§f" +
                        "\n> §echannel§f";
            }
            case 2: {
                return switch (args[1]) {
                    case "group" -> "§e(Help)§f Invite subcommmands" +
                                "\n> §esend <alias> <invitee>§f" +
                                "\n> §erevoke <alias> <invitee>§f" +
                                "\n> §eaccept <alias>§f" +
                                "\n> §ereject <alias>§f";
                    case "invite" -> "§e(Help)§f Group subcommands" +
                                "\n> §ecreate <name> <alias> <colour>§f" +
                                "\n> §edisband <alias>§f" +
                                "\n> §eleave <alias>§f" +
                                "\n> §eremove <alias> <member>§f";
                    case "channel" -> "§b---------------------- §e(Help | Channel)§b ----------------------§f" +
                                "\n> §fchannel <alias>";
                    default -> "§e(Help) §e§lCGroup v" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() +
                            "\n> §egroup§f" +
                            "\n> §einvite§f" +
                            "\n> §echannel§f";
                };
            }
            default: return "§c(Error)§f Too many arguments";
        }
    }
}
