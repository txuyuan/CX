package plugin.CGroup.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import plugin.CGroup.types.Group;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CchCompleter implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions;

        if (!command.getName().equalsIgnoreCase("cch") || !(sender instanceof Player))
            return Arrays.asList("");


        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();

        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(Bukkit.getPluginManager().getPlugin("CX").getDataFolder(), "groupdata.yml"));
        if (args.length == 1) {
            if (data.getConfigurationSection("groups") != null) {
                completions = data.getConfigurationSection("groups").getKeys(false).stream().map(key -> data.getObject("groups." + key, Group.class)).map(group -> group.getAlias()).collect(Collectors.toList());
                completions.add("ALL");
            } else
                completions = Arrays.asList("ALL");
        } else
            completions = Arrays.asList("");
        return completions.stream().filter(argument -> argument.indexOf(args[args.length - 1]) == 0).sorted().collect(Collectors.toList());
    }
}