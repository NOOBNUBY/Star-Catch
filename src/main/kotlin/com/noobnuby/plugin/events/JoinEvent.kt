package com.noobnuby.plugin.events

import com.noobnuby.plugin.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinEvent:Listener {
    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        val p = e.player
        val config = Main.instance.config
        if (config.contains("lobby")) {
            p.teleportAsync(config.getLocation("lobby")!!)
        }
    }
}