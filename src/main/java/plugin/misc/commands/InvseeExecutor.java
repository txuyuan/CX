package plugin.misc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.misc.managers.InvToString;

public class InvseeExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if ((commandSender instanceof Player) && !commandSender.hasPermission("cx.invsee")) {
            commandSender.sendMessage("§c(Error)§f You do not have permission to use this command");
            return true;
        }
        if (args.length < 1) {
            commandSender.sendMessage("§c(Error)§f No player specified");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            commandSender.sendMessage("§c(Error)§f §7" + args[0] + "§f is not online");
            return true;
        }

        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.openInventory(target.getInventory());
        }else
            commandSender.sendMessage("§9(Info)§f §7" + args[0] + "§f has this following inventory: " + InvToString.toString(target.getInventory()));

        return true;
    }
}
