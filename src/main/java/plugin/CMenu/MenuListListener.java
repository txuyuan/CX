package plugin.CMenu;

import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import plugin.Data.PluginFile;

import java.util.List;
import java.util.UUID;

public class MenuListListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        FileConfiguration fConfig = PluginFile.getFile("cMenuFile");
        List<String> idList = (List<String>) fConfig.getList("players");
        List<Player> pList = null;
        for (String str : idList)
            if (Bukkit.getPlayer(UUID.fromString(str)) != null)
                pList.add(Bukkit.getPlayer(UUID.fromString(str)));
        for (Player player : event)
            if (pList.contains(player))
                player.remove();

        System.out.println("§7(CMenu) Ping from " + event.getAddress());
    }

}
