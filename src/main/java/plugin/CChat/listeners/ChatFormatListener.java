package plugin.CChat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import plugin.CX.Main;

public class ChatFormatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        event.setMessage(event.getMessage().replace("&","§").replace("\\§","&").replace("%","%%"));
        event.setFormat("(§a" + player.getDisplayName() + "§f) " + event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        new BukkitRunnable(){
            @Override
            public void run() {
                Player player = event.getPlayer();
                String msg = player.getDisplayName() + "§r logged in at " + getLocationDesc(player.getLocation());
                Main.logInfo(msg);

                String playerMsg = "§a(Join)§6 " + player.getDisplayName() + "§f joined the game";
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(playerMsg));
            }
        }.runTaskLater(Main.getInstance(), 1);

    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        new BukkitRunnable(){
            @Override
            public void run() {
                Player player = event.getPlayer();
                String msg = player.getDisplayName() + "§r logged out at " + getLocationDesc(player.getLocation());
                Main.logInfo(msg);

                String playerMsg = "§a(Leave)§6 " + player.getDisplayName() + "§f left the game";
                Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(playerMsg));
            }
        }.runTaskLater(Main.getInstance(), 1);
    }



    private String getLocationDesc(Location loc){
        String msg = "§nx: " + loc.getBlockX() + ", y: " + loc.getBlockY() + ", z: " + loc.getBlockZ() + "§f in world: §n" + loc.getWorld().getName();
        return msg;
    }

}
