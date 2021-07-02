package plugin.CChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SayExec implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
		
		String senderName = "Console";
		String message = String.join(" ", args);
		message = ChatColor.translateAlternateColorCodes('&', message);
		if (sender instanceof Player) {
			Player player = (Player)sender;
			senderName = ((Player)sender).getDisplayName();
			if (player.isOp() && args[0].equalsIgnoreCase("p")) {
				message = message.substring(2);
				Bukkit.broadcastMessage(message);
			}
			else 
				Bukkit.broadcastMessage("§6§oALL§f | §a" + senderName + "§f >> " + message);
			return true;
		}else {
			if (args[0].equalsIgnoreCase("p"))
				message = message.substring(2);
			Bukkit.broadcastMessage("(§bConsole§f) " + message);
			return true;
		}
		
	}
	
}
