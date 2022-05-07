package plugin.CX;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.CChat.commands.SayExec;
import plugin.CChat.listeners.ChatFormatListener;
import plugin.CChat.listeners.DeathMsgListener;
import plugin.CHome.commands.ChomeExec;
import plugin.CHome.commands.ChomeTabCompleter;
import plugin.CHome.listeners.DeathListener;
import plugin.CMenu.CMenuExec;
import plugin.CMenu.MenuListListener;
import plugin.misc.MiscManager;
import plugin.misc.compatibility.ThirdLife.ThirdLifeCompatibility;
import plugin.misc.listeners.SpectatorTPListener;
import plugin.misc.others.Recipes;

import java.io.IOException;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("CX");
    }

    public static void logInfo(String msg) {
        getInstance().getLogger().info(msg);
    }

    public static void logDiskError(IOException e) {
        getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Error writing to disk: \n" + e.getStackTrace().toString());

    }

    public static void logTest(String msg) {
        boolean isDebug = false;
        if (isDebug) logInfo("§aTest: " + msg);
    }



    public void onEnable() {
        getDataFolder().mkdir();

        getCommand("say").setExecutor(new SayExec());
        getServer().getPluginManager().registerEvents(new ChatFormatListener(), this);
        getServer().getPluginManager().registerEvents(new DeathMsgListener(), this);

        getCommand("chome").setExecutor(new ChomeExec());
        getCommand("chome").setTabCompleter(new ChomeTabCompleter());
        getServer().getPluginManager().registerEvents(new DeathListener(), this);

        getCommand("cmenu").setExecutor(new CMenuExec());
        getServer().getPluginManager().registerEvents(new MenuListListener(), this);

        MiscManager.register(this);
        Recipes.register();
        ThirdLifeCompatibility.check();

        logInfo("§b(Status)§f Plugin enabled");
    }

    public void onDisable() {
        HandlerList.unregisterAll(new DeathListener());
        HandlerList.unregisterAll(new ChatFormatListener());
        HandlerList.unregisterAll(new MenuListListener());
        HandlerList.unregisterAll(new SpectatorTPListener());
        ThirdLifeCompatibility.cleanup();
        logInfo("§b(Status)§f Plugin disabled");
    }
}