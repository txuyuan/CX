package plugin.misc.compatibility.ThirdLife;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import plugin.CX.Main;

public class ThirdLifeCompatibility {

    private static boolean isThirdLife;

    public static void check(){
        if(Bukkit.getPluginManager().getPlugin("ThirdLife") != null){
            isThirdLife = true;
            Main.getInstance().getServer().getPluginManager().registerEvents(new PlayerJoinListener(), Main.getInstance());
        }else
            isThirdLife = false;
    }

    public static void cleanup(){
        if(isThirdLife)
            HandlerList.unregisterAll(new PlayerJoinListener());

    }

}
