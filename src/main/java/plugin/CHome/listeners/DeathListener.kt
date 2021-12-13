package plugin.CHome.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import plugin.CHome.commands.ChomePlayer

class DeathListener : Listener{

    class DeathPointListener : Listener {
        @EventHandler
        fun onDeath(event: PlayerDeathEvent?) {
            ChomePlayer.setDeath(event!!)
        }
    }


}