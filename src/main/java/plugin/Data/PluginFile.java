package plugin.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

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
            System.out.println("§c(Error)§f §cError writing to disk");
            exception.printStackTrace();
        }
    }

}
