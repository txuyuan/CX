package plugin.CHome;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import plugin.CX.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.UUID;

import plugin.CX.Main;
import java.util.logging.Level;

import org.bukkit.entity.Player;

public class CmdExecute {

    private static void save(FileConfiguration d, File dFile, Player p){
        try {
            d.save(dFile);
        }
        catch (IOException exception) {
            Main.getInstance().getLogger().log(Level.INFO, "§c(Error)§f §cError writing to disk");
            p.sendMessage("§c(Error)§f Error writing to disk");
            exception.printStackTrace();
        }
    }



	static String setshop(Player p) {
        if (!p.hasPermission("chome.admin")) {
            Main.getInstance().getLogger().log(Level.INFO, "§b(Status)§f §e" + p.getName() + "§f Attempted to set the shopping district without permissions");
            return "§c(Error)§f You do not have permission to set the shopping district";
        }
        Location l = p.getLocation();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = YamlConfiguration.loadConfiguration(dFile);
        d.set("shop", l);
        save(d, dFile, p);
        return "§b(Status)§f Successfully set shopping district";
    }
    
    static String shop(Player p) {
        if (!p.hasPermission("chome.shop")) {
            Main.getInstance().getLogger().log(Level.INFO, "§b(Status)§f §e" + p.getName() + "§f Atempted to teleport to the shopping district without permissions");
            return "§c(Error)§f You do not have permission to teleport to the shopping district";
        }
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation("shop");
        if(l == null)
            return "§c(Error)§f The shopping district is not set";
        if(p.hasPermission("chome.admin")){
            p.teleport(l);
            return "§b(Status)§f Teleporting to shopping district";
        }
        if (p.getWorld() != Bukkit.getWorlds().get(0))
            return "§c(Error)§f You cannot teleport to the shop while not in the overworld";
        new BukkitRunnable() {
            public void run() {
                p.teleport(l);
            }
        }.runTaskLater(Main.getInstance(), 60);
        return "§b(Status)§f Teleporting to shopping district...";
    }
	
    static String sethome(Player p) {
        if (!p.hasPermission("chome.sethome")) {
            Main.getInstance().getLogger().log(Level.INFO, "§b(Status)§f §e" + p.getName() + "§f Attempted to set home without permission");
            return "§c(Error)§f You do not have permission to set your home";
        }
        Location l = p.getLocation();
        String u = p.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = YamlConfiguration.loadConfiguration(dFile);
        d.set(u + ".home", l);
        save(d, dFile, p);
        return "§b(Status)§f Successfully set your home";
    }

    public static void home(Player player, String[] args){
        if(player.hasPermission("chome.admin")){
            if(args.length>1){
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                Location l = getHome(target.getUniqueId());
                if(l!=null){
                    player.teleport(l);
                    player.sendMessage("§b(Status)§f Teleporting to " + target.getName() + "'s home");
                }else player.sendMessage("§b(Status)§f " + target.getName() + " does not have a set home");
            }else{
                Location l = getHome(player.getUniqueId());
                if(l!=null){
                    player.teleport(l);
                    player.sendMessage("§b(Status)§f Teleporting to your home");
                }else player.sendMessage("§b(Status)§f You do not have a set home");
            }
        }else{
            if (!player.hasPermission("chome.home")) {
                Main.getInstance().getLogger().log(Level.INFO, "§b(Status) §e" + player.getName() + "§f Attempted to teleport to home without permission");
                player.sendMessage("§c(Error)§f You do not have permission to teleport to your home");
            }
            Location loc = getHome(player.getUniqueId());
            if (loc != null) {
                new BukkitRunnable() {
                    public void run() { player.teleport(loc); }
                }.runTaskLater(Main.getInstance(), 60);
                player.sendMessage("§b(Status)§f Teleporting to your home...");
            }
            else player.sendMessage("§b(Status)§f You do not have a set home");
        }
    }

    private static Location getHome(UUID uuid){
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation(uuid.toString() + ".home");
        return l;
    }
    
    static void setDeath(PlayerDeathEvent e) {
        LivingEntity e2 = (LivingEntity)e.getEntity();
        Main.getInstance().getLogger().log(Level.INFO, "CHOME | DEATH >> " + e2.getName() + " has died at " + e2.getLocation());
        String u = e2.getUniqueId().toString();
        Location l = e2.getLocation();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = YamlConfiguration.loadConfiguration(dFile);
        d.set(u + ".death", l);
        d.set(u + ".death-used", false);
        Player p = (Player)e2;
        save(d, dFile, p);
    }
    
    static String death(Player p) {
        String u = p.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation(u + ".death");
        if (l == null) 
            return "§c(Error)§f You have not died yet";
        if (d.getBoolean(u + ".death-used"))
            return "§c(Error)§f You can only teleport to your home once";
        d.set(u + ".death-used", true);
        save(d, dFile, p);
        if(!p.hasPermission("chome.death")){
            Main.getInstance().getLogger().log(Level.INFO, "§b(Status)§f §e" + p.getName() + "§fAttempted to teleport to their last death point without permission");
            return "§b(Status)§f You do not have permission to teleport to your last death point";
        }
        if (p.hasPermission("chome.admin")) {
        	p.teleport(l);
            return "§b(Status)§f Teleporting to your last death point";
        }
        new BukkitRunnable() {
            public void run() {
                p.teleport(l);
            }
        }.runTaskLater(Main.getInstance(), 60);
        return "§b(Status)§f Teleporting to your last death point...";
    }
}
