package plugin.misc;

import plugin.CX.Main;
import plugin.misc.commands.BeeExecutor;
import plugin.misc.commands.EnderchestExecutor;
import plugin.misc.commands.InvseeExecutor;
import plugin.misc.listeners.SpectatorTPListener;

public class MiscManager {

    public static void register(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(new SpectatorTPListener(), Main.getInstance());
        plugin.getCommand("enderchest").setExecutor(new EnderchestExecutor());
        plugin.getCommand("invsee").setExecutor(new InvseeExecutor());
        plugin.getCommand("beezooka").setExecutor(new BeeExecutor());
        //plugin.getCommand("anvil").setExecutor(new AnvilExecutor()); //TODO: Fix anvil
    }

}
