package plugin.misc.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import plugin.CX.Main;
import plugin.misc.managers.DelayManager;

public class BeeExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if ((commandSender instanceof Player) && !commandSender.hasPermission("cx.enderChest")) {
            commandSender.sendMessage("§c(Error)§f You do not have permission to use this command");
            return true;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§c(Error)§f You must be a player to use this command");
        }
        if (args.length < 1) {
            commandSender.sendMessage("§c(Error)§f No player specified");
            return true;
        }

        Player player = (Player)commandSender;
        Entity bee = player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.BEE);
        bee.setVelocity(player.getLocation().add(0, 1, 0).toVector().multiply(2));

        DelayManager.beeKill(bee);

        return false;
    }
}
