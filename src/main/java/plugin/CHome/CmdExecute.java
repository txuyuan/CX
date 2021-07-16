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

public class CmdExecute {

    private static void save(FileConfiguration d, File dFile, Player p){
        try {
            d.save(dFile);
        }
        catch (IOException exception) {
            System.out.println("§c(Error)§f §cError writing to disk");
            p.sendMessage("§c(Error)§f Error writing to disk");
            exception.printStackTrace();
        }
    }



	static String setshop(Player p) {
        if (!p.hasPermission("chome.setshop")) {
            System.out.println("§b(Status)§f §e" + p.getName() + "§f Attempted to set the shopping district without permissions");
            return "§c(Error)§f You do not have permission to set the shopping district";
        }
        Location l = p.getLocation();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        d.set("shop", (Object)l);
        save(d, dFile, p);
        return "§b(Status)§f Successfully set shopping district";
    }
    
    static String shop(Player p) {
        if (!p.hasPermission("chome.shop")) {
            System.out.println("§b(Status)§f §e" + p.getName() + "§f Atempted to teleport to the shopping district without permissions");
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
            System.out.println("§b(Status)§f §e" + p.getName() + "§f Attempted to set home without permission");
            return "§c(Error)§f You do not have permission to set your home";
        }
        Location l = p.getLocation();
        String u = p.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        d.set(String.valueOf(String.valueOf(u)) + ".home", (Object)l);
        save(d, dFile, p);
        return "§b(Status)§f Successfully set your home";
    }

    static String home(Player player, String[] args){
        if(player.hasPermission("chome.home.others")){
            Player target = Bukkit.getPlayer(args[2]);
            if(args.length>2 && target!=null){
                Location l = getHome(target);
                if(l!=null){
                    player.teleport(l);
                    return "§b(Status)§f Teleporting to " + target.getName() + "'s home...";
                }
                else return "§b(Status)§f " + target.getName() + " does not have a set home";
            }
            Location l = getHome(player);
            if(l!=null){
                player.teleport(l);
                return "§b(Status)§f Teleporting to your home...";
            }
            else return "§b(Status)§f You do not have a set home";
        }else{
            if (!player.hasPermission("chome.home")) {
                System.out.println("§b(Status) §e" + player.getName() + "§f Attempted to teleport to home without permission");
                return "§c(Error)§f You do not have permission to teleport to your home";
            }
            Location loc = getHome(player);
            if (loc != null) {
                new BukkitRunnable() {
                    public void run() { player.teleport(loc); }
                }.runTaskLater(Main.getInstance(), 60);
                return "§b(Status)§f Teleporting to your home...";
            }
            else return "§b(Status)§f You do not have a set home";
        }
    }

    private static Location getHome(Player player){
        String u = player.getUniqueId().toString();
        File dFile = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration d = (FileConfiguration)YamlConfiguration.loadConfiguration(dFile);
        Location l = d.getLocation(String.valueOf(String.valueOf(u)) + ".home");
        return l;
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
        Player p = (Player)e2;
        save(d, dFile, p);
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
