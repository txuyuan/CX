package plugin.CHome.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.CX.Main;

public class ChomeManager implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command c, String label, String[] args) {

        if (args.length == 0)
            s.sendMessage("§c(Error)§f No arguments specified");

        if (s instanceof Player) {
            Player player = (Player) s;
            switch (args[0].toLowerCase()) {
                case "sethome":
                    player.sendMessage(PlayerChome.sethome(player));
                    break;
                case "home":
                    PlayerChome.home(player, args);
                    break;
                case "death":
                    player.sendMessage(PlayerChome.death(player));
                    break;
                case "setshop":
                    player.sendMessage(PlayerChome.setshop(player));
                    break;
                case "shop":
                    player.sendMessage(PlayerChome.shop(player));
                    break;
                case "help":
                    String msg = "§e(Help)§f §e§lCHOME v" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() + "\n" + helpMsg(player.isOp());
                    player.sendMessage(msg);
                    break;
                default:
                    player.sendMessage("§c(Error) §fUnrecognised argument");
            }
        } else {
            switch (args[0].toLowerCase()) {
                case "home":
                    ConsoleChome.getLocation(args, "home");
                    break;
                case "death":
                    ConsoleChome.getLocation(args, "death");
                    break;
                case "help":
                    String reply = "§e(Help) §e§lCHOME V" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() +
                            "\n§b--------------------- §eConsole Commands§b ---------------------" +
                            "\n§e(Help)§7 /chome home <target>§f: Get location of target's home" +
                            "\n§e(Help)§7 /chome death §f: Get target's last death location" +
                            "\n§b----------------- §ePlayer Commands (Admin)§b ------------------\n" +
                            helpMsg(true) +
                            "\n§b------------------------------------------------------------§f";
                    Main.logInfo(reply);
                    break;
            }
        }
        return true;
    }

    private String helpMsg(boolean isOp) {
        String help = "§e(Help)§7 /chome sethome §f: Set your home" +
                (isOp ? "\n§e(Help)§7 /chome home <target>§f: Teleport to target's home \n         (leave blank for own home)" : "\n§e(Help)§7 /chome home§f: Teleport to your home") +
                "\n§e(Help)§7 /chome death §f: Teleport to your last death location" +
                "\n§e(Help)§7 /chome shop §f: Teleport to shopping district" +
                (isOp ? "\n§e(Help)§7 /chome setshop §f Set location of shopping district" : "") +
                "\n§e(Help)§7 /chome help §f: Show this help message";
        return help;
    }

}