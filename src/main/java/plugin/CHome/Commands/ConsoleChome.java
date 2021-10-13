package plugin.CHome.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import plugin.CX.Main;

public class ConsoleChome {

    static void getLocation(String[] args, String target) {
        if (args.length < 2) {
            Main.logInfo("(Error) No target found");
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
        Location loc = PlayerChome.getLocation(player.getUniqueId() + "." + target);

        String reply = "§b(Status)§f " + player.getName() + "'s §f" + target + " is at §nx: " + loc.getBlockX() + ", y: " + loc.getBlockY() + ", z: " + loc.getBlockZ() +
                "§f in world: §n" + loc.getWorld().getName();
        Main.logInfo(reply);
    }

}
