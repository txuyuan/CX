package plugin.CHome.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

object ChomeConsole {

    var fileC = YamlConfiguration.loadConfiguration( File("./plugins/CX/chomedata.yml") )

    fun exec(sender: CommandSender, args: Array<out String>): String{
        fileC = YamlConfiguration.loadConfiguration( File("./plugins/CX/chomedata.yml") )

        return when(args.get(0).lowercase()){
            "death" -> chomeInfo(Destination.DEATH, args)
            "shop" -> chomeInfo(Destination.SHOP, args)
            "home" -> chomeInfo(Destination.HOME, args)
            "help" -> consoleHelpMsg()
            else -> "§c(Error)§f Unrecognised argument ${args.get(0)} \n ${chomeHelpMsg(false)}"
        }
    }

    fun chomeInfo(destination: Destination, args: Array<out String>): String{
        var loc: Location
        var destName: String
        if(destination==Destination.SHOP){
            loc = ChomePlayer.getLocation("shop")!!
            destName = "the shopping district"
        }else{
            if(args.size < 2) return "§c(Error)§f Target player required"
            val player = Bukkit.getOfflinePlayer(args.get(1))
            if(player==null) return "§c(Error)§f Unrecognised player ${args.get(1)}"

            loc = ChomePlayer.getLocation(destination.toPath(player.uniqueId))!!
            destName = getPlayerName(player)
        }

        return "§9(Info)§f ${destName}'s home is at §nx: ${loc.blockX}, y:${loc.blockY}, z:${loc.blockZ}§f in world: §n${loc.world.name}"
    }


    private fun getPlayerName(target: OfflinePlayer): String {
        val targetName: String
        if(target.isOnline){
            val player = (target as? Player)!!
            targetName = player.displayName
        }
        else targetName = target.name!!
        return targetName
    }


    private fun consoleHelpMsg(): String{
        return """
            §e(Help) §e§lCHOME V${Bukkit.getPluginManager().getPlugin("CX")!!.description.version}
            §b--------------------- §eConsole Commands§b ---------------------
            §e(Help)§7 /chome home <target>§f: Get location of target's home
            §e(Help)§7 /chome death §f: Get target's last death location
            §b----------------- §ePlayer Commands (Admin)§b ------------------
            ${chomeHelpMsg(true)}
            §b------------------------------------------------------------§f
         """.trimIndent()
    }

}