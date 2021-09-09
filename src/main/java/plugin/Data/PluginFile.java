package plugin.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import plugin.CX.Main;
import java.util.logging.Level;

public class PluginFile{

    public static FileConfiguration getFile(String name){
        File file = new File("./plugins/CX", name);
        FileConfiguration fConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        return fConfig;
    }

    public static void save(FileConfiguration fConfig, String name){
        try {
            File file = new File("./plugins/CX", name);
            fConfig.save(file);
        }
        catch (IOException exception) {
            Main.getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f §cError writing to disk");
            exception.printStackTrace();
        }
    }

}
