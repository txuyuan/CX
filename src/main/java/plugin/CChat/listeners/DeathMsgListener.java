package plugin.CChat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMsgListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(event.getDeathMessage().replace(event.getEntity().getName(), event.getEntity().getDisplayName()));
        String name = event.getEntity().getDisplayName().replace("§e", "§a").replace("§c", "§e").replace("§d", "§c").replace("§7", "§d");
    }


}
