package com.noobnuby.plugin.events

import com.noobnuby.plugin.utils.Data
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class DamageEvent:Listener {
    @EventHandler
    fun isDamagePlayer(e: EntityDamageEvent) {
        if (!Data.isGameStart) {
            e.isCancelled = true
        }
    }
}