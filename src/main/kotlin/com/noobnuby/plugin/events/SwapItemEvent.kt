package com.noobnuby.plugin.events

import com.noobnuby.plugin.utils.Data
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class SwapItemEvent:Listener {
    @EventHandler
    fun onSwapItem(e:PlayerSwapHandItemsEvent) {
        if (Data.isGameStart) e.isCancelled = true
    }
}