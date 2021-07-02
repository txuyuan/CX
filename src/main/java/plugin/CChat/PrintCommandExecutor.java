package plugin.CChat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class PrintCommandExecutor implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.hasPermission("cchat.print")) {
            sender.sendMessage("§c(Error)§f You do not have permission to use print");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("§c(Error)§f No argument specified");
            return true;
        }
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§e(Help)§f §7/print <msg>§f to print message");
            return true;
        }
        if (args.length <= 0) {
            return false;
        }
        String message = args[0];
        if (args.length > 0) {
            for (int i = 2; i < args.length; ++i) {
                message = String.valueOf(message) + " " + args[i];
            }
            message.replace("&", "§").replace("\\&", "&");
            Bukkit.broadcastMessage(message);
            return true;
        }
        sender.sendMessage("§c(Error)§f No message specified");
        return true;
    }
}
