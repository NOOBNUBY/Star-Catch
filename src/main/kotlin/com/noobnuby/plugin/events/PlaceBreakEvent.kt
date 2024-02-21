package com.noobnuby.plugin.events

import com.noobnuby.plugin.utils.Data
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class PlaceBreakEvent:Listener {
    @EventHandler
    fun onBlockBreak(e:BlockBreakEvent) {
        val p = e.player
        if (!Data.isGameStart) {
            if (p.isOp) return
        }
        e.isCancelled = true
    }

    @EventHandler
    fun onPlaveBlock(e:BlockPlaceEvent) {
        val p = e.player
        if (!Data.isGameStart) {
            if (p.isOp) return
        }
        e.isCancelled = true
    }
}