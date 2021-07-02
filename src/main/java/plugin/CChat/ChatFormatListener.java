package plugin.CChat;

import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.stream.Collectors;
import java.util.UUID;
import java.io.IOException;
import plugin.CGroup.Group;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.Listener;

public class ChatFormatListener implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
        FileConfiguration data = (FileConfiguration)YamlConfiguration.loadConfiguration(dataFile);
        String channelAlias = data.getString("players." + event.getPlayer().getUniqueId().toString() + ".channel", "ALL");
        String channel;
        if (channelAlias.equals("ALL")) 
            channel = "§6§oALL§r";
        else {
            Group group = Group.getGroup(channelAlias, data);
            if (group == null) {
                channel = "§6§oALL§r";
                data.set("players." + event.getPlayer().getUniqueId().toString() + ".channel", (Object)"ALL");
                try {
                    data.save(dataFile);}
                catch (IOException exception) {
                    exception.printStackTrace();
                    System.out.println("§c(Error)§f Failed to write to disk");
                    event.getPlayer().sendMessage("§c(Error)§f Failed to write to disk");
                }
                event.getPlayer().sendMessage("§9(Info)§f The channel you were in was deleted\n§b(Status)§f Now messaging in §eGlobal");
                event.setCancelled(true);
            }
            
            else if (!group.getMembers().contains(event.getPlayer().getUniqueId().toString())) {
                channel = "§6§oALL§r";
                data.set("players." + event.getPlayer().getUniqueId().toString() + ".channel", (Object)"ALL");
                try {
                    data.save(dataFile);}
                catch (IOException exception) {
                    exception.printStackTrace();
                    System.out.println("§c(Error)§f Failed to write to disk");
                    event.getPlayer().sendMessage("§c(Error)§f Failed to write to disk");
                }
                event.getPlayer().sendMessage("§9(Info)§f The channel you were in was deleted, or you were removed\n§b(Status)§f Now messaging in §eGlobal");
                event.setCancelled(true);
            }
            else {
            	channel = group.getFormattedAlias();
                Set<Player> recipients = event.getRecipients();
                recipients.clear();
                group.getMembers().stream().map(UUID::fromString).map(Bukkit::getPlayer).filter(player -> player != null).forEach(player -> recipients.add(player));
            }
        }
        event.setMessage(event.getMessage().replace("&", "§").replace("\\&", "&"));
        event.setFormat(String.valueOf(channel) + "§f | §a%s§f >> %s");
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        System.out.println(String.valueOf(player.getName()) + "logged in at" + player.getLocation());
        event.setJoinMessage("§a(Join)§f " + player.getDisplayName() + "§a joined the game");
        notifs(event, player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        System.out.println(String.valueOf(player.getName()) + "logged out at" + player.getLocation());
        event.setQuitMessage("§a(Leave)§f " + player.getDisplayName() + "§a left the game");
    }
    
    
    
    private static void notifs(PlayerJoinEvent event, Player player) {
    	
    	File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
    	FileConfiguration data = YamlConfiguration.loadConfiguration((File)dataFile);
    	
        if (data.getConfigurationSection("groups") != null) {
        	List<String> invites = data.getConfigurationSection("groups").getKeys(false).stream().map(key -> (Group)data.getObject("groups." + key, Group.class)).filter
            		(group -> group.getInvites().contains(player.getUniqueId().toString())).map(group -> group.getAlias()).collect(Collectors.toList());
        	for (String groupAli : invites) {
        		Group inviteGroup = Group.getGroup(groupAli, data);
        		player.sendMessage("§e(Info)§f You have been invited to join " + inviteGroup.getFormattedName() + " by §e" + Bukkit.getOfflinePlayer(UUID.fromString(inviteGroup.getOwner())).getName()
        				+ "\n§f§e(Info)§f Use §7/cgroup invite accept " + inviteGroup.getAlias() + "§f to accept invitation");
        	}
        }
        
        if (data.getList("players." + player.getUniqueId().toString() + ".disList") != null) {
        	String search = "players." + player.getUniqueId().toString() + ".disList";
        	List<String> disList = (List<String>)data.getList("players." + player.getUniqueId().toString() + ".disList");
        	for (String dis : disList) {
        		player.sendMessage("§e(Info)§f The group §e" + dis + "§f (which you were a part of) was disbanded");
        	}
        	data.set(search, null);
        	try {
                data.save(dataFile);}
            catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("§c(Error)§f Failed to write to disk");}
        }
        
        if (data.getList("players." + player.getUniqueId().toString() + ".leaveList") != null){
        	String search = "players." + player.getUniqueId().toString() + ".leaveList";
        	List<String> leavList = (List<String>)data.getList("players." + player.getUniqueId().toString() + ".leaveList");
        	for (String leav : leavList) {
        		List<String> leavs = Arrays.asList(leav.split("`"));
        		System.out.println(leavs);
        		player.sendMessage("§e(Info)§f Player §e" + leavs.get(0) + "§f left the group §e" + leavs.get(1));
        	}
        	data.set(search, null);
        	try {
                data.save(dataFile);}
            catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("§c(Error)§f Failed to write to disk");}
        }
        
        if (data.getList("players." + player.getUniqueId().toString() + ".transList") != null){
        	String search = "players." + player.getUniqueId().toString() + ".transList";
        	List<String> transList = (List<String>)data.getList("players." + player.getUniqueId().toString() + ".transList");
        	for (String trans : transList) {
        		List<String> transInfo = Arrays.asList(trans.split("`"));
        		player.sendMessage("§e(Info)§f Ownership of grooup §e" + transInfo.get(1) + "§f has been transferred to player §e" + transInfo.get(0));
        	}
        	data.set(search, null);
        	try {
                data.save(dataFile);}
            catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("§c(Error)§f Failed to write to disk");}
        }
    	
    }
}
