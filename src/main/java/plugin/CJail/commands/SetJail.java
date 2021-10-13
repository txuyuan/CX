package plugin.CJail.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.CJail.JailManager;

public class SetJail implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("cx.setjail")) {
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c(Erorr)§f You must be a player to create a jail");
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage("§c(Error)§f Specify name");
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage(JailManager.newJail(args[0], player.getLocation()));
        return true;
    }
}
