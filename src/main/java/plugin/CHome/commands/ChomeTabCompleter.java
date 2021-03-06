package plugin.CHome.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import plugin.CX.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChomeTabCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        List<String> onlinePlayers = new ArrayList<>();
        Main.getInstance().getServer().getOnlinePlayers().forEach(player -> onlinePlayers.add(player.getName()));

        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                completions = Arrays.asList("home", "death", "help");
            } else if (args.length == 2)
                completions = onlinePlayers;
        } else {

            if (args.length == 1) {
                completions.add("death");
                completions.add("home");
                completions.add("sethome");
                completions.add("shop");
                completions.add("help");
                if (sender.hasPermission("chome.admin")) completions.add("setshop");
            } else {
                if (!sender.hasPermission("chome.admin") || args[0] == "help" || args[0] == "shop")
                    return Arrays.asList();
                else if (args[0] == "death" || args[0] == "home")
                    completions = onlinePlayers;
                else return Arrays.asList();
            }

        }

        return completions.stream().filter(argument -> argument.indexOf(args[args.length - 1]) == 0).sorted().toList();
    }
}