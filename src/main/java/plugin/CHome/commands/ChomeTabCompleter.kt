package plugin.CHome.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import plugin.CX.Main
import java.util.*
import java.util.stream.Collectors

class ChomeTabCompleter : TabCompleter {


    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String>? {
        var completions: MutableList<String> = ArrayList()
        val onlinePlayers: MutableList<String> = ArrayList()
        Main.getInstance().server.onlinePlayers.forEach { player: Player? -> onlinePlayers.add(player!!.name) }
        if (!command.name.equals("chome", ignoreCase = true)) return Arrays.asList()
        if (sender !is Player) {
            if (args.size == 1) completions = Arrays.asList("home", "death", "help") else if (args.size == 2) completions = onlinePlayers
        } else {
            if (args.size == 1) {
                completions.add("death")
                completions.add("home")
                completions.add("sethome")
                completions.add("shop")
                completions.add("help")
                if (sender.hasPermission("chome.admin")) completions.add("setshop")
            } else {
                completions = if (!sender.hasPermission("chome.admin") || args[0] === "help" || args[0] === "shop") return Arrays.asList() else if (args[0] === "death" || args[0] === "home") onlinePlayers else return Arrays.asList()
            }
        }
        return completions.stream().filter { argument: String -> argument.indexOf(args[args.size - 1]) == 0 }.sorted().collect(Collectors.toList())
    }


}