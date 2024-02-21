package com.noobnuby.plugin.events

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent

class FoodEvent:Listener {
    @EventHandler
    fun onChangeFoodLevel(e: FoodLevelChangeEvent) {
        e.isCancelled = true
    }
}