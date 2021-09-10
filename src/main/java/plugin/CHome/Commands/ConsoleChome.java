package plugin.CHome.Commands;

import org.bukkit.entity.Player;
import plugin.CX.Main;

import java.util.logging.Level;

public class ConsoleChome {

    static void getLocation(String[] args, String target){
        if(args.length < 2){
            Main.getPrinter().log(Level.INFO, "(Error) No target found");
        }

    }

}
