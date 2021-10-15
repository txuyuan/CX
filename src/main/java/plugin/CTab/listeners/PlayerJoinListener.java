package plugin.CTab.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.data.PluginFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        loadTabList(event.getPlayer());
    }



    public static void loadTabList(Player player){
        FileConfiguration fileC = PluginFile.getFile("tablist");

        List<?> headerFile = fileC.getList("header");
        List<?> footerFile = fileC.getList("footer");

        if(headerFile==null)
            fileC.set("header", Arrays.asList("Sample header"));
        else {
            List<String> headers = headerFile.stream().map(e -> e.toString()).collect(Collectors.toList());
            player.setPlayerListHeader(String.join("\n", headers));
        }

        if(footerFile==null)
            fileC.set("footer", Arrays.asList("Sample footer"));
        else {
            List<String> footers = footerFile.stream().map(e -> e.toString()).collect(Collectors.toList());
            player.setPlayerListFooter(String.join("\n", footers));
        }

        PluginFile.save(fileC, "tablist");
    }

}
