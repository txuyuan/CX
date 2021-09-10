package plugin.CHome;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CHomeTabCompleter implements TabCompleter
{
    public List<String> onTabComplete(CommandSender sender,  Command command,  String alias,  String[] args) {
        List<String> completions;
        boolean isAdmin = !(sender instanceof Player) || sender.hasPermission("chome.admin");

        if(!command.getName().equalsIgnoreCase("chome"))
            return Arrays.asList("");

        if(args.length == 1){
            completions = Arrays.asList("death", "home", "sethome", "shop", "help");
            if(!(sender instanceof Player) || sender.hasPermission("chome.admin")) completions.add("setshop");
            if(!(sender instanceof Player)) completions.remove("sethome");
        }else{
            if(!isAdmin || args[0] == "help" || args[0] == "shop"){
                return Arrays.asList("");}
            if(args[0] == "death" || args[0] == "home")
                return null;
            return Arrays.asList("");
        }
        return completions;
    }
}
