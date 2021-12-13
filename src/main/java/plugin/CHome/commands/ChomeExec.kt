package plugin.CHome.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ChomeExec: CommandExecutor{

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if(args.size < 1){
            sender.sendMessage("§c(Error)§f No arguments specified")
            sender.sendMessage(chomeHelpMsg(sender.hasPermission("chome.admin")))
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
            §e(Help)§7 /chome sethome §f: Set your home${if (isOp) "\n§e(Help)§7 /chome home <target>§f: Teleport to target's home \n         (leave blank for own home)" else "\n§e(Help)§7 /chome home§f: Teleport to your home"}
            §e(Help)§7 /chome death §f: Teleport to your last death location
            §e(Help)§7 /chome shop §f: Teleport to shopping district${if (isOp) "\n§e(Help)§7 /chome setshop §f Set location of shopping district" else ""}
            §e(Help)§7 /chome help §f: Show this help message
            """.trimIndent()
}