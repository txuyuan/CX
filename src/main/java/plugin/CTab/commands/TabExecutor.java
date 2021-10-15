package plugin.CTab.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.CTab.listeners.PlayerJoinListener;

public class TabExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if((sender instanceof Player) && !sender.hasPermission("cx.tablist")){
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }

        if(args.length==0){
            sender.sendMessage("§c(Error)§f No arguments specified");
            return true;
        }

        switch(args[0]){
            case "reload":
                Bukkit.getOnlinePlayers().forEach(player -> PlayerJoinListener.loadTabList(player));
                sender.sendMessage("§b(Status)§f Tablist reloaded");
                break;
            default:
                sender.sendMessage("§c(Error)§f Unrecognised argument");
                break;
        }

        return true;
    }
}
