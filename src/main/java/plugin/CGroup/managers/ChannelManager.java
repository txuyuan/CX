package plugin.CGroup.managers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import plugin.CGroup.types.Group;
import plugin.CX.Main;

import java.io.File;
import java.io.IOException;

public class ChannelManager {
    public static String switchChannel(Player player, String[] args) {
        switch (args.length) {
            case 1: {
                return "§c(Error)§f Please specify a channel\n§3(Info)§f §3Do §7/cgroup help§f for a list of commands and syntax";
            }
            case 2: {
                File dataFile = new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml");
                FileConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
                String channel = args[1].toUpperCase();
                data.set("players." + player.getUniqueId() + ".channel", "ALL");
                try {
                    data.save(dataFile);
                } catch (IOException exception) {
                    Main.logDiskError(exception);
                    return "§c(Error)§f Error writing to disk";
                }
                Group group = Group.getGroup(channel, data);
                if (channel.equals("ALL"))
                    return "§b(Status)§f Now messaging in §eGlobal";
                if (group == null)
                    return "§c(Error)§f A group with alias §f§o" + channel + "§c does not exist" + "\n§b(Status)§f Now messaging in §eGlobal";
                if (group.getMembers().contains(player.getUniqueId().toString()) || player.isOp()) {
                    data.set("players." + player.getUniqueId() + ".channel", channel);
                    try {
                        data.save(dataFile);
                    } catch (IOException exception2) {
                        exception2.printStackTrace();
                        Main.logDiskError(exception2);
                        return "§c(Error)§f Error writing to disk";
                    }
                    return "§b(Status)§f Now messaging in " + group.getFormattedName();
                }
                return "§c(Error)§f You are not a member of §f§o" + group.getFormattedName() + "\n§b(Status)§f Now messaging in §eGlobal";
            }
            default:
                return "§c(Error)§f Too many arguments specified\n§3(Info)§f Do §7/cgroup help§f for a list of commands and syntax";
        }
    }
}