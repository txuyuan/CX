package plugin.CHome.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.scheduler.BukkitRunnable
import plugin.CX.Main
import java.io.File
import java.io.IOException
import java.util.*

object ChomePlayer {

    var file = File("./plugins/CX/chomedata.yml")
    var fileC = YamlConfiguration.loadConfiguration( File("./plugins/CX/chomedata.yml") )
    fun isAdmin(player: Player): Boolean{
        return player.hasPermission("chome.admin")
    }

    fun exec(player: Player, args: Array<out String>) : String{
        file = File("./plugins/CX/chomedata.yml")
        if(!file.exists()) {
            file.createNewFile()
            file = File("./plugins/CX/chomedata.yml")
        }
        fileC = YamlConfiguration.loadConfiguration( File("./plugins/CX/chomedata.yml") )

        return when(args.get(0).lowercase()){
            "sethome" -> setHomeShop(player, Destination.HOME)
            "setshop" -> setHomeShop(player, Destination.SHOP)
            "home" -> tpHome(player, args)
            "shop" -> tpChome(player, Destination.SHOP)
            "death" -> tpChome(player, Destination.DEATH)
            "help" -> chomeHelpMsg(isAdmin(player))
            else -> "§c(Error)§f Unrecognised argument ${args.get(0)} \n ${chomeHelpMsg(false)}"
        }
    }



    fun setDeath(event: PlayerDeathEvent){
        val player = event.player
        setDeathUsed(player, true)
        saveLocation(player.location, Destination.DEATH, player)
    }
    private fun setDeathUsed(player: Player, used: Boolean): Boolean{
        fileC.set("${player.uniqueId}.death-used", used)
        try {
            return true
            fileC.save(file)
        } catch (exception: IOException) {
            return false
            Main.logDiskError(exception)
        }
    }


    fun tpDeath(player: Player, args: Array<out String>): String{
        if(fileC.getBoolean("${player.uniqueId}.death-used"))
            return "§c(Error)§f You can only teleport to your home once"
        val msg = tpChome(player, Destination.DEATH)
        return tpChome(player, Destination.DEATH)
    }

    fun tpHome(player: Player, args: Array<out String>) : String{
        if(isAdmin(player) && args.size > 1) return infoHome(player, args)
        else return tpChome(player, Destination.HOME)
    }

    fun infoHome(player: CommandSender, args: Array<out String>): String{
        val target = Bukkit.getOfflinePlayer(args[1]!!)
        val loc = getLocation(Destination.HOME.toPath(target.uniqueId))
        if(loc==null)
            return "§c(Error)§f Unrecognised player ${target.name}"

        val targetName = {
            if(target.isOnline)
                (target as Player).displayName
            else target.name
        }
        if(player is Player)
            return teleport(player, loc, null, "$targetName's home")
        else
            return "§9(Info)§f ${player.name}'s home is at §nx: ${loc.blockX}, y:${loc.blockY}, z:${loc.blockZ}§f in world: §n${loc.world.name}"

    }

    fun tpChome(player: Player, destination: Destination): String{
        val permError = checkPermission(player, "chome.${destination.toLabel()}")
        if(!permError.isNullOrEmpty()) return permError as String

        val loc = getLocation(destination.toPath(player.uniqueId)) ?: return "§c(Error)§f ${destination.toString().replaceFirstChar { it.uppercase() }} does not exist"

        if(destination==Destination.DEATH) setDeathUsed(player, true)
        return teleport(player, loc, destination)
    }

    fun setHomeShop(player: Player, destination: Destination): String {
        val permError = checkPermission(player, "chome.set${destination.toLabel()}")
        if(!permError.isNullOrEmpty()) return permError

        if(saveLocation(player.getLocation(), destination, player))
            return "§b(Status)§f Successfully set $destination"
        else return "§c(Error)§f Error writing to disk"
    }




// ----------- Tools ------------

    private fun checkPermission(player: Player, permission: String): String?{
        if(!player.hasPermission(permission)){
            Main.logInfo("§c(Error)§f §e${player.name}§f attempted to ${permission.replace("chome.","")} without permission")
            return "§c(Error)§f You do not have permission to do this"
        }else return null
    }

    private fun teleport(player: Player, location: Location, destination: Destination?, destinationName: String = "placeholder"): String{
        if(isAdmin(player)){
            player.teleport(location)
            return "§b(Status)§f Teleporting to $destination"
        }
        if(destination==Destination.SHOP && player.world != Bukkit.getWorlds()[0])
            return "§c(Error)§f You cannot teleport to the shop while not in the overworld"

        var countUp = 1
        object: BukkitRunnable(){
            override fun run() {
                if(countUp==3) player.teleport(location)

                val colour = if(countUp==3) "e" else "b"
                val fadeIn = if(countUp==1) 10 else 0
                val fadeOut = if(countUp==3) 10 else 0
                player.sendTitle("§$colour§l${">".repeat(countUp)} §f§lTeleporting... §$colour§l${"<".repeat(countUp)} ", "§b§oYou can move :)", fadeIn, 20, fadeOut)

                if(countUp==3) this.cancel()
                countUp++
            }
        }.runTaskTimer(Main.getInstance(), 1, 20)
        val destinationDisplay =
                if(destination!=null) destination
                else destinationName

        return "§b(Status)§f Teleporting to $destination..."
    }

    private fun saveLocation(location: Location?, destination: Destination, player: Player): Boolean {
        val path = "${destination.toPath(player.uniqueId)}"
        fileC[path!!] = location
        try {
            fileC.save(file)
            return true
        } catch (exception: IOException) {
            Main.logDiskError(exception)
            return false
        }
    }
    fun getLocation(path: String?): Location? {
        return fileC.getLocation(path!!)
    }


}


enum class Destination {
    HOME, DEATH, SHOP;

    override fun toString(): String {
        return when(this){
            HOME -> "your home"
            DEATH -> "your last death location"
            SHOP -> "the shopping district"
        }
    }
    fun toPath(uuid: UUID): String{
        return when(this){
            HOME -> "$uuid.home"
            DEATH -> "$uuid.death"
            SHOP -> "shop"
        }
    }
    fun toLabel(): String{
        return when(this){
            HOME -> "home"
            DEATH -> "death"
            SHOP -> "shop"
        }
    }
}