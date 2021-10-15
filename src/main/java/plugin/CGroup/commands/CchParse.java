package plugin.CGroup.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import plugin.CGroup.types.Group;
import plugin.CX.Main;

import java.io.File;
import java.io.IOException;

public class CchParse implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c(Error)§f You must be a player to use this command");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("cgroup.use")) {
            sender.sendMessage("§c(Error)§f You do not have permission to do this");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("§c(Error)§f Please specify a channel\n§3(Info)§f Do §7/cgroup help§f for a list of commands and syntax");
            return true;
        }

        if (args.length == 1) {
            File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
            String channel = args[0].toUpperCase();
            data.set("players." + player.getUniqueId() + ".channel", "ALL");
            try {
                data.save(dataFile);
            } catch (IOException exception) {
                Main.logDiskError(exception);
                player.sendMessage("§c(Error)§f Error writing to disk");
                return true;
            }
            Group group = Group.getGroup(channel, data);
            if (channel.equals("ALL")) {
                player.sendMessage("§b(Status)§f Now messaging in §eGlobal");
                return true;
            }
            if (group == null) {
                player.sendMessage("§b(Error)§f A group with alias §f§o" + channel + "§f does not exist" + "\n§b(Status)§f Now messaging in §eGlobal");
                return true;
            }
            if (group.getMembers().contains(player.getUniqueId().toString()) || player.isOp()) {
                data.set("players." + player.getUniqueId() + ".channel", channel);
                try {
                    data.save(dataFile);
                } catch (IOException exception2) {
                    Main.logDiskError(exception2);
                    player.sendMessage("§c(Error)§f Error writing to disk");
                    return true;
                }
                player.sendMessage("§b(Status)§f Now messaging in " + group.getFormattedName());
                return true;
            }
            player.sendMessage("§c(Error)§f You are not a member of §f§o" + group.getFormattedName() + "\n§b(Status)§f Now messaging in §eGlobal");
            return true;
        } else {
            player.sendMessage("§c(Error)§f Too many arguments specified \n§3(Info)§f Do §7/cgroup help §ffor a list of commands and syntax");
            return true;
        }

    }
}
        
