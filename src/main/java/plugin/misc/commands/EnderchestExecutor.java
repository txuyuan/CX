package plugin.misc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.misc.managers.InvToString;

public class EnderchestExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String lbl, String[] args) {

        if ((commandSender instanceof Player) && !commandSender.hasPermission("cx.enderChest")) {
            commandSender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        Player target;
        if (args.length < 1) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("§c(Error)§f No player specified");
                return true;
            }
            target = (Player) commandSender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                commandSender.sendMessage("§c(Error)§f §7" + args[0] + "§f is not online");
                return true;
            }
        }

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.openInventory(target.getEnderChest());
        } else {
            commandSender.sendMessage("§3(Info)§f §7" + args[0] + "§f has this following enderchest: " + InvToString.toString(target.getEnderChest()));
        }
        return true;
    }
}
