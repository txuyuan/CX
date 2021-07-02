package plugin.CHome;

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
import org.bukkit.entity.Player;

public class CmdExecute
{
	
	static String setshop(Player p) {
        if (!p.hasPermission("chome.setshop")) {
            System.out.println("§b(Status)§f §e" + p.getName() + "§fAttempted to set the shopping district without permissions");
            return "§c(Error)§f You do not have permission to set the shopping district";
        }
        Location l = p.getLocation();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        d.set("shop", (Object)l);
        try {
            d.save(dFile);
        }
        catch (IOException exception) {
            System.out.println("§c(Error)§f §cError writing to disk");
            p.sendMessage("§c(Error)§f Error writing to disk");
            exception.printStackTrace();
        }
        return "§b(Status)§f Successfully set shopping district";
    }
    
    static String shop(Player p) {
        if (!p.hasPermission("chome.shop")) {
            System.out.println("§b(Status)§f §e" + p.getName() + "§fAtempted to teleport to the shopping district without permissions");
            return "§c(Error)§f You do not have permission to teleport to the shopping district";
        }
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation("shop");
        if (p.getWorld() != Bukkit.getWorlds().get(0)) 
        	return "§c(Error)§f You cannot teleport to the shop while not in the overworld";
        if (l != null) {
        	new BukkitRunnable() {
        		public void run() {
        			p.teleport(l);
        		}
        	}.runTaskLater(Main.getInstance(), 60);
            return "§b(Status)§f Teleporting to shopping district";
        }
        return "§c(Error)§f The shopping district is not set";
    }
	
    static String sethome(Player p) {
        if (!p.hasPermission("chome.sethome")) {
            System.out.println("§b(Status)§f §e" + p.getName() + "§fAttempted to set home without permission");
            return "§c(Error)§f You do not have permission to set your home";
        }
        Location l = p.getLocation();
        String u = p.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        d.set(String.valueOf(String.valueOf(u)) + ".home", (Object)l);
        try {
            d.save(dFile);}
        catch (IOException exception) {
            System.out.println("§c(Error)§f §cError writing to disk");
            p.sendMessage("§c(Error)§f Error writing to disk");
            exception.printStackTrace();
        }
        return "§b(Status)§f Successfully set your home";
    }
    
    static String home(Player p) {
        if (!p.hasPermission("chome.home")) {
            System.out.println("§b(Status)§f §e" + p.getName() + "§fAttempted to teleport to home without permission");
            return "§c(Error)§f You do not have permission to teleport to your home";
        }
        String u = p.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation(String.valueOf(String.valueOf(u)) + ".home");
        if (l != null) {
        	new BukkitRunnable() {
        		public void run() {
        			p.teleport(l);
        		}
        	}.runTaskLater(Main.getInstance(), 60);
        	return "§b(Status)§f Teleporting to your home...";
        }
        return "§c(Error)§f Your home is not set";
    }
    
    static void setDeath(PlayerDeathEvent e) {
        LivingEntity e2 = (LivingEntity)e.getEntity();
        System.out.println("CHOME | DEATH >> " + e2.getName() + " has died at " + e2.getLocation());
        String u = e2.getUniqueId().toString();
        Location l = e2.getLocation();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        d.set(String.valueOf(String.valueOf(u)) + ".death", (Object)l);
        d.set(String.valueOf(String.valueOf(u)) + ".death-used", (Object)false);
        try {
            d.save(dFile);}
        catch (IOException exception) {
            System.out.println("CHOME | §fERROR§f >> §cError writing to disk");
            e.getEntity().sendMessage("§c(Error)§f Error writing to disk");
            exception.printStackTrace();
        }
    }
    
    static String death(Player p) {
        String u = p.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation(String.valueOf(String.valueOf(u)) + ".death");
        if (l == null) 
            return "§c(Error)§f You have not died yet";
        if (d.getBoolean(String.valueOf(String.valueOf(u)) + ".death-used")) 
            return "§c(Error)§f You can only teleport to your home once";
        d.set(String.valueOf(String.valueOf(u)) + ".death-used", (Object)true);
        try {
            d.save(dFile);}
        catch (IOException exception) {
            System.out.println("§c(Error)§f Error writing to disk");
            p.sendMessage("§c(Error)§f Error writing to disk");
            exception.printStackTrace();
        }
        if (p.hasPermission("chome.death")) {
        	new BukkitRunnable() {
        		public void run() {
        			p.teleport(l);
        		}
        	}.runTaskLater(Main.getInstance(), 60);
            return "§b(Status)§f Teleporting to your last death point";
        }
        System.out.println("§b(Status)§f §e" + p.getName() + "§fAttempted to teleport to their last death point without permission");
        return "§b(Status)§f You do not have permission to teleport to your last death point";
    }
}
