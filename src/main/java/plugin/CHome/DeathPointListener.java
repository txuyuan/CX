package plugin.CHome;


import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;

public class DeathPointListener implements Listener
{
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        CmdExecute.setDeath(e);
    }
}
