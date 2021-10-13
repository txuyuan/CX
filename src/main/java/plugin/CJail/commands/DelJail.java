package plugin.CJail.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import plugin.CJail.JailManager;

public class DelJail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("cx.deljail")) {
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§c(Error)§f Specify name");
            return true;
        }

        sender.sendMessage(JailManager.delJail(args[0]));
        return true;
    }
}
