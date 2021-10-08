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
import plugin.misc.commands.BeeExecutor;
import plugin.misc.listeners.SpectatorTPListener;
import plugin.misc.commands.EnderchestExecutor;
import plugin.misc.commands.InvseeExecutor;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    //private static Main plugin;

    public static Logger getPrinter() {
        return Main.getInstance().getLogger();
    }

    public static Plugin getInstance() {
        return Bukkit.getPluginManager().getPlugin("CX");
    }

    public void onEnable() {
        this.getDataFolder().mkdir();
        this.getCommand("chome").setExecutor(new ChomeManager());
        this.getCommand("chome").setTabCompleter(new CHomeTabCompleter());
        this.getServer().getPluginManager().registerEvents(new DeathPointListener(), this);

        this.getCommand("say").setExecutor(new SayExec());
        this.getServer().getPluginManager().registerEvents(new ChatFormatListener(), this);

        this.getCommand("cgroup").setExecutor(new CGroupCmdParse());
        this.getCommand("cgroup").setTabCompleter(new Completer());
        this.getCommand("cch").setExecutor(new CchParse());
        this.getCommand("cch").setTabCompleter(new CchCompleter());
        ConfigurationSerialization.registerClass(Group.class, "Group");

        this.getCommand("cmenu").setExecutor(new CMenuExec());
        this.getServer().getPluginManager().registerEvents(new MenuListListener(), this);

        this.getServer().getPluginManager().registerEvents(new SpectatorTPListener(), this);
        this.getCommand("enderchest").setExecutor(new EnderchestExecutor());
        this.getCommand("invsee").setExecutor(new InvseeExecutor());
        this.getCommand("beezooka").setExecutor(new BeeExecutor());

        Bukkit.addRecipe(getRecipe());

        //plugin = this;
        getPrinter().log(Level.INFO, "(§aSTATUS§f) §9Plugin successfully enabled");
    }

    public void onDisable() {
        HandlerList.unregisterAll(new DeathPointListener());
        HandlerList.unregisterAll(new ChatFormatListener());
        HandlerList.unregisterAll(new MenuListListener());
        HandlerList.unregisterAll(new SpectatorTPListener());
        getPrinter().log(Level.INFO, "CX | §aSTATUS§f >> §9Plugin successfully disabled");
    }

    private ShapedRecipe getRecipe() {
        ItemStack result = new ItemStack(Material.BUNDLE);
        NamespacedKey key = new NamespacedKey(this, "bundle");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("SRS", "R R", "RRR");
        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('R', Material.RABBIT_HIDE);
        return recipe;
    }
}
