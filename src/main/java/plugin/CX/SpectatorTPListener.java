package plugin.CX;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SpectatorTPListener implements Listener {

    @EventHandler
    public void onPlayerTeleportEvent(PlayerTeleportEvent event) {
        if(!event.isCancelled() && !event.getPlayer().hasPermission("cx.allowSpecTp") && event.getPlayer().getGameMode()==GameMode.SPECTATOR
        && event.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE))
            event.setCancelled(true);
    }

}
