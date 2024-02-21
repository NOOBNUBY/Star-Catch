package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main
import com.noobnuby.plugin.utils.Data
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class RespawnEvent:Listener {
    @EventHandler
    fun onRespawn(e:PlayerRespawnEvent) {
        if(!Data.isGameStart) return
        val p = e.player
        Data.PlayerLastLoc[p]?.let { respawnLocation ->
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance,  {
                p.teleport(respawnLocation)
                Data.PlayerLastLoc.remove(p)
                p.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS,20,10,true))
                p.addPotionEffect(PotionEffect(PotionEffectType.SLOW,20,10,true))
            }, 1)
        }
    }
}