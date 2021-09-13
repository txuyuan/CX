package plugin.CChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CChatTabCompleter implements TabCompleter {
    public List<String> onTabComplete(final CommandSender s, final Command c, final String al, final String[] a) {
        if (c.getName().equals("cchat") && a.length < 2 && s instanceof Player) {
            final List<String> defList = new ArrayList<String>();
            final List<String> rList = new ArrayList<String>();
            defList.add("stats");
            for (final String str : defList)
                if (str.indexOf(a[0]) == 0)
                    rList.add(str);
            if (a[0] == "stats") {
                final List<String> defStatsList = new ArrayList<String>();
                final List<String> rStatsList = new ArrayList<String>();
                defList.add("stats");
                for (final String str2 : defStatsList)
                    if (str2.indexOf(a[0]) == 0)
                        rStatsList.add(str2);
            }
            return rList;
        }
        return null;
    }
}
