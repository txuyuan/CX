package plugin.CX;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
import plugin.misc.commands.BeeExecutor;
import plugin.misc.commands.EnderchestExecutor;
import plugin.misc.commands.InvseeExecutor;
import plugin.misc.listeners.SpectatorTPListener;
import plugin.misc.others.Recipes;

import java.io.IOException;
import java.util.logging.Level;

public class Main extends JavaPlugin {


    public static void logInfo(String msg) {
        getInstance().getLogger().log(Level.INFO, msg);
    }

    public static void logDiskError(IOException e) {
        getInstance().getLogger().log(Level.SEVERE, "§c(Error)§f Error writing to disk: \n" + e.getStackTrace().toString());
    }


    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("CX");
    }

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

        logInfo("(§aSTATUS§f) §9Plugin successfully enabled");
    }

    public void onDisable() {
        HandlerList.unregisterAll(new DeathPointListener());
        HandlerList.unregisterAll(new ChatFormatListener());
        HandlerList.unregisterAll(new MenuListListener());
        HandlerList.unregisterAll(new SpectatorTPListener());
        logInfo("CX | §aSTATUS§f >> §9Plugin successfully disabled");
    }


}
