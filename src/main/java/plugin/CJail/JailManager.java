package plugin.CJail;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import plugin.CX.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JailManager {

    public static String newJail(String name, Location loc) {
        FileConfiguration fileC = getFile();

        if (fileC.get(name) != null)
            return "§c(Error)§f §7" + name + "§f already exists";

        fileC.set(name, loc);
        if (!saveFile(fileC)) return "§c(Error)§f Error saving to disk";

        Main.logInfo("§b(Status) Jail " + name + " created at " + loc.getBlockX() + ", " + loc.getBlockZ() + ", " + loc.getBlockY() + " in " + loc.getWorld());
        return "§b(Status)§f §7" + name + "§f jail created";
    }

    public static List<String> getJails() {
        FileConfiguration fileC = getFile();

        List<String> jails = new ArrayList<>();
        fileC.getKeys(false).forEach(jail -> jails.add(jail));

        return jails;
    }

    public static String delJail(String name) {
        FileConfiguration fileC = getFile();

        if (fileC.get(name) == null)
            return "§c(Error)§f Jail §7" + name + "§f does not exist";

        fileC.set(name, null);
        if (!saveFile(fileC)) return "§c(Error)§f Error saving to disk";

        String reply = "§b(Status)§f §7" + name + "§f deleted";
        Main.logInfo(reply);
        return reply;
    }


    private static FileConfiguration getFile() {
        File file = new File("./plugins/CX", "jails.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    private static boolean saveFile(FileConfiguration fileC) {
        File file = new File("./plugins/CX", "jails.yml");
        try {
            fileC.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
