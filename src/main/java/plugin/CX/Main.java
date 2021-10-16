package plugin.CX;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.CChat.commands.SayExec;
import plugin.CChat.listeners.ChatFormatListener;
import plugin.CChat.listeners.DeathMsgListener;
import plugin.CGroup.commands.CGroupCmdParse;
import plugin.CGroup.commands.CGroupCompleter;
import plugin.CGroup.commands.CchCompleter;
import plugin.CGroup.commands.CchParse;
import plugin.CGroup.types.Group;
import plugin.CHome.commands.CHomeTabCompleter;
import plugin.CHome.commands.ChomeManager;
import plugin.CHome.listeners.DeathPointListener;
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
        getInstance().getLogger().log(Level.INFO, msg);
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

        getCommand("cgroup").setExecutor(new CGroupCmdParse());
        getCommand("cgroup").setTabCompleter(new CGroupCompleter());
        getCommand("cch").setExecutor(new CchParse());
        getCommand("cch").setTabCompleter(new CchCompleter());
        ConfigurationSerialization.registerClass(Group.class, "Group");

        getCommand("chome").setExecutor(new ChomeManager());
        getCommand("chome").setTabCompleter(new CHomeTabCompleter());
        getServer().getPluginManager().registerEvents(new DeathPointListener(), this);

        getCommand("cmenu").setExecutor(new CMenuExec());
        getServer().getPluginManager().registerEvents(new MenuListListener(), this);

        MiscManager.register(this);
        Recipes.register();
        ThirdLifeCompatibility.check();

        logInfo("§b(Status)§f Plugin enabled");
    }

    public void onDisable() {
        HandlerList.unregisterAll(new DeathPointListener());
        HandlerList.unregisterAll(new ChatFormatListener());
        HandlerList.unregisterAll(new MenuListListener());
        HandlerList.unregisterAll(new SpectatorTPListener());
        ThirdLifeCompatibility.cleanup();
        logInfo("§b(Status)§f Plugin disabled");
    }
}
