package plugin.CMenu;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import plugin.CX.Main;
import plugin.Data.PluginFile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MenuListListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        FileConfiguration fConfig = PluginFile.getFile("cMenuFile");
        List<String> idList = (List<String>) fConfig.getList("players");
        if (idList == null)
            return;
        List<Player> pList = new ArrayList<>();
        for (String str : idList)
            if (Bukkit.getPlayer(UUID.fromString(str)) != null)
                pList.add(Bukkit.getPlayer(UUID.fromString(str)));
        Iterator<Player> it = event.iterator();
        while (it.hasNext()) {
            Player player = it.next();
            if (pList.contains(player)) it.remove();
        }

        Main.logInfo("§7Ping from " + event.getAddress());
    }

}
