package plugin.CHome;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import plugin.CHome.Commands.PlayerChome;

public class DeathPointListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        PlayerChome.setDeath(e);
    }
}
