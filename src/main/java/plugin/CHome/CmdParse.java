// 
// Decompiled by Procyon v0.5.36
// 

package plugin.CHome;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CmdParse implements CommandExecutor
{
    public boolean onCommand(final CommandSender s, final Command c, final String lbl, final String[] a) {
        if (s instanceof Player) {
            final Player p = (Player)s;
            if (a.length < 1) 
                s.sendMessage("§c(Error)§f No arguments specified");
            else if (a[0].equals("sethome")) 
                s.sendMessage(CmdExecute.sethome(p));
            else if (a[0].equals("home")) 
                s.sendMessage(CmdExecute.home(p));
            else if (a[0].equals("death")) 
                s.sendMessage(CmdExecute.death(p));
            else if (a[0].equals("setshop"))
            	s.sendMessage(CmdExecute.setshop(p));
            else if (a[0].equals("shop"))
            	s.sendMessage(CmdExecute.shop(p));
            else if (a[0].equals("help")) {
                s.sendMessage("§e(Help)§f §e§lCHOME V" + Bukkit.getPluginManager().getPlugin("CX").getDescription().getVersion() + "§f"
                		+ "\n§e(Help)§f §7/chome sethome §f: Set your home§f"
                		+ "\n§e(Help)§f §7/chome home §f: Teleport to your home§f"
                		+ "\n§e(Help)§f §7/chome death §f: Teleport to your last death location§f"
                		+ "\n§e(Help)§f §7/chome help §f: Show this help message§f");
            }
            else 
                s.sendMessage("§c(Error) §fUnrecognised argument");
        }
        else 
            s.sendMessage("§c(Error)§f You must be a player to use CHome");
        return true;
    }
}
