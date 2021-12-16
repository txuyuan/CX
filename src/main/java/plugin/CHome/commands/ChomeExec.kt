package plugin.CHome.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChomeExec: CommandExecutor{

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(args.size < 1){
            sender.sendMessage("§c(Error)§f No arguments specified")
            sender.sendMessage(chomeHelpMsg(sender.hasPermission("chome.admin")))
            return true
        }

        if(sender is Player){
            val player = sender as Player
            sender.sendMessage(ChomePlayer.exec(player, args))
        }else{
            sender.sendMessage(ChomeConsole.exec(sender, args))
        }
        return true
    }

}

fun chomeHelpMsg(isOp: Boolean): String {

    return """
§e(Help)§f §b§lCX v${Bukkit.getPluginManager().getPlugin("CX")!!.description.version}§f
> §esethome§f
${if (isOp) "> §ehome <target>§f" else "> §ehome§f"}
> §edeath§f
> §eshop§f
${if (isOp) "§e<setshop>§f" else ""} """.trimIndent()

}