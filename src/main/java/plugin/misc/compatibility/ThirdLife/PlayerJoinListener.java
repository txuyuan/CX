package plugin.misc.compatibility.ThirdLife;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import plugin.CX.Main;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        PermissionAttachment playerAttach = event.getPlayer().addAttachment(Main.getInstance(), 100);
        playerAttach.setPermission("cx.opName", false);
    }

}
