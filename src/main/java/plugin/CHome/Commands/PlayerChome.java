package plugin.CHome.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import plugin.CX.Main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerChome {

     static void saveLocation(Location location, String path, Player player){
        File file = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration fileC = YamlConfiguration.loadConfiguration(file);
        fileC.set(path, location);
        try{ fileC.save(file);}
        catch(IOException exception){
            Main.getPrinter().log(Level.INFO, "§c(Error)§f §cError writing to disk");
            player.sendMessage("§c(Error)§f Error writing to disk");
        }
    }

     static Location getLocation(String path){
        File file = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration fileC = YamlConfiguration.loadConfiguration(file);
        return fileC.getLocation(path);
    }

     private enum Destination{
        HOME, DEATH, SHOP;
        public String toString(){
            switch (this){
                case HOME: return "your home";
                case DEATH: return "your last death point";
                case SHOP: return "the shopping district";
                default: return null;
            }
        }
    }

    private static String teleport(Player player, Location location, Destination destination){
        if(player.hasPermission("chome.admin")){
            player.teleport(location);
            return "§b(Status)§f Teleporting to " + destination.toString();
        }
        if (destination == Destination.SHOP && player.getWorld() != Bukkit.getWorlds().get(0))
            return "§c(Error)§f You cannot teleport to the shop while not in the overworld";
        new BukkitRunnable() {
            public void run() {
                player.teleport(location);
            }
        }.runTaskLater(Main.getInstance(), 60);
        return "§b(Status)§f Teleporting to " + destination.toString() + "...";
    }

    //------

    static String setshop(Player p) {
        if (!p.hasPermission("chome.admin")) {
            Main.getPrinter().log(Level.INFO, "§b(Status)§e" + p.getName() + "§f attempted to set the shopping district without permissions");
            return "§c(Error)§f You do not have permission to set the shopping district";
        }
        saveLocation(p.getLocation(), "shop", p);
        return "§b(Status)§f Successfully set shopping district";
    }

    static String shop(Player p) {
        if (!p.hasPermission("chome.shop")) {
            Main.getPrinter().log(Level.INFO, "§b(Status)§e" + p.getName() + "§f atempted to teleport to the shopping district without permissions");
            return "§c(Error)§f You do not have permission to teleport to the shopping district";
        }
        Location shop = getLocation("shop");
        if(shop == null)
            return "§c(Error)§f The shopping district is not set";
        return teleport(p, shop, Destination.SHOP);
    }

    static String sethome(Player p) {
        if (!p.hasPermission("chome.sethome")) {
            Main.getPrinter().log(Level.INFO, "§b(Status)§f §e" + p.getName() + "§f Attempted to set home without permission");
            return "§c(Error)§f You do not have permission to set your home";
        }
        String uuid = p.getUniqueId().toString();
        saveLocation(p.getLocation(), (uuid + ".home"), p);
        return "§b(Status)§f Successfully set your home";
    }

    //-----

    private static Location getHome(UUID uuid){
        return getLocation(uuid.toString() + ".home");
    }

    static void home(Player player, String[] args){
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
                Main.getPrinter().log(Level.INFO, "§b(Status) §e" + player.getName() + "§f Attempted to teleport to home without permission");
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

    //-----

    private static void setDeathUsed(Player player, boolean used){
        File file = new File("./plugins/CX", "chomedata.yml");
        FileConfiguration fileC = YamlConfiguration.loadConfiguration(file);
        fileC.set(player.getUniqueId().toString() + ".death-used", used);
        try{ fileC.save(file);}
        catch(IOException e) {
            Main.getPrinter().log(Level.INFO, "§c(Error)§f §cError writing to disk");
            player.sendMessage("§c(Error)§f Error writing to disk");
        }
    }

    static void setDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Main.getPrinter().log(Level.INFO, "(CHOME | DEATH) " + player.getName() + " has died at " + player.getLocation());
        saveLocation(player.getLocation(), player.getUniqueId().toString() + ".death", player);
        setDeathUsed(player, false);
    }

    static String death(Player p) {
        Location l = getLocation(p.getUniqueId().toString() + ".death");
        if (l == null)
            return "§c(Error)§f You have not died yet";

        FileConfiguration fileC = YamlConfiguration.loadConfiguration(new File("./plugins/CX", "chomedata.yml"));
        if (fileC.getBoolean(p.getUniqueId().toString() + ".death-used"))
            return "§c(Error)§f You can only teleport to your home once";
        setDeathUsed(p, true);

        if(!p.hasPermission("chome.death")){
            Main.getPrinter().log(Level.INFO, "§b(Status)§f §e" + p.getName() + "§fAttempted to teleport to their last death point without permission");
            return "§b(Status)§f You do not have permission to teleport to your last death point";
        }
        return teleport(p, l, Destination.DEATH);
    }

}