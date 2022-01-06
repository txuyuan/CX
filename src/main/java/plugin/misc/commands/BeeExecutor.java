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

public class BeeExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if ((commandSender instanceof Player) && !commandSender.hasPermission("cx.beezooka")) {
            commandSender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§c(Error)§f You must be a player to use this command");
        }

        Player player = (Player) commandSender;
        Entity bee = player.getWorld().spawnEntity(player.getLocation().add(0, 1, 0), EntityType.BEE);
        bee.setVelocity(player.getLocation().add(0, 1, 0).getDirection().multiply(2));

        new BukkitRunnable() {
            public void run() {
                Location loc = bee.getLocation();
                ((Damageable) bee).damage(100);
                loc.getWorld().createExplosion(loc, 0F);
            }
        }.runTaskLater(Main.getInstance(), 25);

        return false;
    }
}
