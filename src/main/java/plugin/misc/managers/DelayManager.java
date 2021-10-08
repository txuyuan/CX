package plugin.misc.managers;

import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import plugin.CX.Main;

public class DelayManager {

    public static void beeKill(Entity bee){
        new BukkitRunnable() {
            public void run() {
                Location loc = bee.getLocation();
                ((Damageable)bee).damage(100);
                loc.getWorld().createExplosion(loc, 0F);
            }
        }.runTaskLater(Main.getInstance(), 60);
    }

}
