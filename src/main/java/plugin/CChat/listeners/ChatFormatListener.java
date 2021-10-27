package plugin.CChat.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import plugin.CGroup.types.Group;
import plugin.CX.Main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChatFormatListener implements Listener {
    private static void notifs(PlayerJoinEvent event, Player player) {

        File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);

        if (data.getConfigurationSection("groups") != null) {
            List<String> invites = data.getConfigurationSection("groups").getKeys(false).stream().map(key -> data.getObject("groups." + key, Group.class)).filter
                    (group -> group.getInvites().contains(player.getUniqueId().toString())).map(group -> group.getAlias()).collect(Collectors.toList());
            for (String groupAli : invites) {
                Group inviteGroup = Group.getGroup(groupAli, data);
                player.sendMessage("§3(Info)§f You have been invited to join " + inviteGroup.getFormattedName() + " by §e" + Bukkit.getOfflinePlayer(UUID.fromString(inviteGroup.getOwner())).getName()
                        + "\n§f§3(Info)§f Use §7/cgroup invite accept " + inviteGroup.getAlias() + "§f to accept invitation");
            }
        }

        if (data.getList("players." + player.getUniqueId() + ".disList") != null) {
            String search = "players." + player.getUniqueId() + ".disList";
            List<String> disList = (List<String>) data.getList("players." + player.getUniqueId() + ".disList");
            for (String dis : disList) {
                player.sendMessage("§3(Info)§f The group §e" + dis + "§f (which you were a part of) was disbanded");
            }
            data.set(search, null);
            try {
                data.save(dataFile);
            } catch (IOException exception) {
                exception.printStackTrace();
                Main.logDiskError(exception);
            }
        }

        if (data.getList("players." + player.getUniqueId() + ".leaveList") != null) {
            String search = "players." + player.getUniqueId() + ".leaveList";
            List<String> leavList = (List<String>) data.getList("players." + player.getUniqueId() + ".leaveList");
            for (String leav : leavList) {
                List<String> leavs = Arrays.asList(leav.split("`"));
                player.sendMessage("§3(Info)§f Player §e" + leavs.get(0) + "§f left the group §e" + leavs.get(1));
            }
            data.set(search, null);
            try {
                data.save(dataFile);
            } catch (IOException exception) {
                exception.printStackTrace();
                Main.logDiskError(exception);
            }
        }

        if (data.getList("players." + player.getUniqueId() + ".transList") != null) {
            String search = "players." + player.getUniqueId() + ".transList";
            List<String> transList = (List<String>) data.getList("players." + player.getUniqueId() + ".transList");
            for (String trans : transList) {
                List<String> transInfo = Arrays.asList(trans.split("`"));
                player.sendMessage("§3(Info)§f Ownership of grooup §e" + transInfo.get(1) + "§f has been transferred to player §e" + transInfo.get(0));
            }
            data.set(search, null);
            try {
                data.save(dataFile);
            } catch (IOException exception) {
                exception.printStackTrace();
                Main.logDiskError(exception);
            }
        }

    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
        String channelAlias = data.getString("players." + player.getUniqueId() + ".channel", "ALL");
        String channel;
        if (channelAlias.equals("ALL"))
            channel = "§6§oALL§r";
        else {
            Group group = Group.getGroup(channelAlias, data);
            if (group == null) {
                channel = "§6§oALL§r";
                data.set("players." + player.getUniqueId() + ".channel", "ALL");
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    exception.printStackTrace();
                    Main.logDiskError(exception);
                    player.sendMessage("§c(Error)§f Error writing to disk");
                }
                player.sendMessage("§3(Info)§f The channel you were in was deleted\n§b(Status)§f Now messaging in §eGlobal");
                event.setCancelled(true);
            } else if (!group.getMembers().contains(player.getUniqueId().toString())) {
                channel = "§6§oALL§r";
                data.set("players." + player.getUniqueId() + ".channel", "ALL");
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    exception.printStackTrace();
                    Main.logDiskError(exception);
                    player.sendMessage("§c(Error)§f Error writing to disk");
                }
                player.sendMessage("§3(Info)§f The channel you were in was deleted, or you were removed\n§b(Status)§f Now messaging in §eGlobal");
                event.setCancelled(true);
            } else {
                channel = group.getFormattedAlias();
                Set<Player> recipients = event.getRecipients();
                recipients.clear();
                recipients = group.getMembers().stream().map(UUID::fromString).map(Bukkit::getPlayer).filter(groupPlayer -> groupPlayer != null).collect(Collectors.toSet());
            }
        }

        event.setMessage(event.getMessage().replace("&", "§").replace("\\&", "&"));

        event.setFormat("(" + channel + "§f | " + (player.hasPermission("cx.opName") ? "§c" : "§a") + player.getDisplayName() + "§f) " + event.getMessage());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Main.logInfo(player.getDisplayName() + " logged in at" + player.getLocation());
        event.setJoinMessage("§a(Join)§6 " + player.getDisplayName() + "§f joined the game");
        notifs(event, player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Main.logInfo(player.getDisplayName() + " logged out at" + player.getLocation());
        event.setQuitMessage("§a(Leave)§6 " + player.getDisplayName() + "§f left the game");
    }
}
