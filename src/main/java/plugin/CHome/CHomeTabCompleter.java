package plugin.CHome;

import java.util.Arrays;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CHomeTabCompleter implements TabCompleter
{
    public List<String> onTabComplete(CommandSender sender,  Command command,  String alias,  String[] args) {
        boolean isAdmin = sender.hasPermission("chome.admin");

        if(!command.getName().equalsIgnoreCase("chome"))
            return Arrays.asList();

        if(!(sender instanceof Player)){
            if(args.length == 1){
                return Arrays.asList("home", "death", "help");
            }
            return null;
        }

        if(args.length == 1){
            List<String> rList = new ArrayList<>();
            rList.add("death"); rList.add("home"); rList.add("sethome"); rList.add("shop"); rList.add("help");
            if((sender).hasPermission("chome.admin")) rList.add("setshop");
            return rList;
        }else{
            if(!isAdmin || args[0] == "help" || args[0] == "shop"){
                return Arrays.asList();}
            if(args[0] == "death" || args[0] == "home")
                return null;
            return Arrays.asList();
        }
    }
}
