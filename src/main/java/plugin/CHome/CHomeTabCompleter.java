package plugin.CHome;

import java.util.Iterator;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CHomeTabCompleter implements TabCompleter
{
    public List<String> onTabComplete(final CommandSender s, final Command c, final String al, final String[] a) {
        if (c.getName().equals("chome") && a.length < 2 && s instanceof Player) {
            final List<String> defList = new ArrayList<String>();
            final List<String> rList = new ArrayList<String>();
            defList.add("help");
            defList.add("death");
            defList.add("home");
            defList.add("sethome");
            defList.add("shop");
            for (final String str : defList) 
                if (str.indexOf(a[0]) == 0) 
                    rList.add(str);
            return rList;
        }
        return null;
    }
}
