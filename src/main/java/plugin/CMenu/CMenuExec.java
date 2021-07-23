package plugin.CMenu;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import plugin.Data.PluginFile;

import java.util.List;

public class CMenuExec implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
        if(!(sender.hasPermission("cmenu.use") || sender instanceof Player)){
            sender.sendMessage("§c(Error)§f You do not have permission to use this");
            return true;
        }
        if(args.length < 0){
            sender.sendMessage("§c(Error)§f No arguments specified");
            return true;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
        if(offlinePlayer == null){
            sender.sendMessage("§c(Error)§7 " + args[1] + "§f is not a recognised player");
            return true;
        }
        if(!offlinePlayer.hasPlayedBefore()){
            sender.sendMessage("§c(Error)§7 " + args[1] + "§f has not played on this server before");
            return true;
        }

        String playerId = offlinePlayer.getUniqueId().toString();
        FileConfiguration fConfig = PluginFile.getFile("cMenuFile");
        List<String> playerList = (List<String>)fConfig.getList("players");
        if(playerList.contains(playerId)){
            playerList.remove(playerId);
            fConfig.set("players", playerList);
            PluginFile.save(fConfig, "cMenuFile");
            sender.sendMessage("§b(Status)§7 " + args[1] + "§f is now hidden on the menu list");
        }else{
            playerList.add(playerId);
            fConfig.set("players", playerList);
            PluginFile.save(fConfig, "cMenuFile");
            sender.sendMessage("§b(Status)§7 " + args[1] + "§f is now shown on the menu list");
        }
        return true;
    }

}
