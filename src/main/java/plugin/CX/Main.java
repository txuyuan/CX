package plugin.CX;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.CChat.ChatFormatListener;
import plugin.CChat.SayExec;
import plugin.CGroup.*;
import plugin.CHome.CHomeTabCompleter;
import plugin.CHome.Commands.ChomeManager;
import plugin.CHome.DeathPointListener;
import plugin.CMenu.CMenuExec;
import plugin.CMenu.MenuListListener;
import plugin.misc.MiscManager;
import plugin.misc.listeners.SpectatorTPListener;
import plugin.misc.others.Recipes;

import java.io.IOException;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public void onEnable() {
        getDataFolder().mkdir();

        getCommand("chome").setExecutor(new ChomeManager());
        getCommand("chome").setTabCompleter(new CHomeTabCompleter());
        getServer().getPluginManager().registerEvents(new DeathPointListener(), this);

        getCommand("say").setExecutor(new SayExec());
        getServer().getPluginManager().registerEvents(new ChatFormatListener(), this);

        getCommand("cgroup").setExecutor(new CGroupCmdParse());
        getCommand("cgroup").setTabCompleter(new Completer());
        getCommand("cch").setExecutor(new CchParse());
        getCommand("cch").setTabCompleter(new CchCompleter());
        ConfigurationSerialization.registerClass(Group.class, "Group");

        getCommand("cmenu").setExecutor(new CMenuExec());
        getServer().getPluginManager().registerEvents(new MenuListListener(), this);

        MiscManager.register(this);
        Recipes.register();

        logInfo("§b(Status)§f Plugin enabled");
    }

    public void onDisable() {
        HandlerList.unregisterAll(new DeathPointListener());
        HandlerList.unregisterAll(new ChatFormatListener());
        HandlerList.unregisterAll(new MenuListListener());
        HandlerList.unregisterAll(new SpectatorTPListener());
        logInfo("§b(Status)§f Plugin disabled");
    }


    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("CX");
    }

    public static void logInfo(String msg) {
        getInstance().getLogger().log(Level.INFO, msg);
    }

    public static void logDiskError(IOException e) {
        getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Error writing to disk: \n" + e.getStackTrace().toString());
    }

}
