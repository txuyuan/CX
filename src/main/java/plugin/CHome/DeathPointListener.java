package plugin.CHome;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathPointListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        CmdExecute.setDeath(e);
    }
}
