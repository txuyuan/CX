package plugin.CX;

import org.bukkit.event.HandlerList;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import plugin.CGroup.Group;
import plugin.CGroup.Completer;
import plugin.CGroup.CGroupCmdParse;
import plugin.CGroup.CchCompleter;
import plugin.CGroup.CchParse;
import plugin.CChat.ChatFormatListener;
import plugin.CChat.SayExec;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import plugin.CHome.DeathPointListener;
import org.bukkit.command.TabCompleter;
import plugin.CHome.CHomeTabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import plugin.CHome.CmdParse;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.CMenu.CMenuExec;
import plugin.CMenu.MenuListListener;

import java.util.logging.Level;

public class Main extends JavaPlugin
{
	private static Main plugin;
	
    public void onEnable() {
        this.getDataFolder().mkdir();
        this.getCommand("chome").setExecutor((CommandExecutor)new CmdParse());
        this.getCommand("chome").setTabCompleter((TabCompleter)new CHomeTabCompleter());
        this.getServer().getPluginManager().registerEvents((Listener)new DeathPointListener(), (Plugin)this);
        
        this.getCommand("say").setExecutor((CommandExecutor)new SayExec());
        this.getServer().getPluginManager().registerEvents((Listener)new ChatFormatListener(), (Plugin)this);
        
        this.getCommand("cgroup").setExecutor((CommandExecutor)new CGroupCmdParse());
        this.getCommand("cgroup").setTabCompleter((TabCompleter)new Completer());
        this.getCommand("cch").setExecutor((CommandExecutor)new CchParse());
        this.getCommand("cch").setTabCompleter((TabCompleter)new CchCompleter());
        ConfigurationSerialization.registerClass((Class)Group.class, "Group");

        this.getCommand("cmenu").setExecutor((CommandExecutor)new CMenuExec());
        this.getServer().getPluginManager().registerEvents((Listener)new MenuListListener(), (Plugin)this);

        this.getServer().getPluginManager().registerEvents((Listener)new SpectatorTPListener(), (Plugin)this);
        
        Bukkit.addRecipe(getRecipe());
        
        plugin = this;

        System.out.println("CX | §aSTATUS§f >> §9Plugin successfully enabled");
    }
    
    public void onDisable() {
        HandlerList.unregisterAll((Listener)new DeathPointListener());
        HandlerList.unregisterAll((Listener)new ChatFormatListener());
        HandlerList.unregisterAll((Listener)new MenuListListener());
        HandlerList.unregisterAll((Listener)new SpectatorTPListener());
        System.out.println("CX | §aSTATUS§f >> §9Plugin successfully disabled");
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
	
	public static Main getInstance() {
		return plugin;
	}
}
