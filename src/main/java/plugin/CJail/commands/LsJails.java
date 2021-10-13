package plugin.CJail.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.CJail.JailManager;

import java.util.List;

public class LsJails implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("cx.listjails") && sender instanceof Player) {
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }

        List<String> jails = JailManager.getJails();
        String reply = "§9(Info)§f ";
        if (jails.size() == 0)
            reply.concat("There are currently no jails");
        else {
            if (jails.size() == 1)
                reply.concat("There is currently this jail: ");
            else reply.concat("There are currently these jails: ");
            reply.concat(String.join(", ", jails));
        }
        sender.sendMessage(reply);

        return true;
    }
}
