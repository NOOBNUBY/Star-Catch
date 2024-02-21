package com.noobnuby.plugin.events

import com.noobnuby.plugin.utils.Data
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent
import org.bukkit.event.player.PlayerDropItemEvent

class ItemEvent:Listener {
    @EventHandler
    fun onClickItem(e: InventoryClickEvent) {
        if (Data.isGameStart) e.isCancelled = true
    }

    @EventHandler
    fun onMoveItem(e: InventoryMoveItemEvent) {
        if (Data.isGameStart) e.isCancelled = true
    }

    @EventHandler
    fun onDragItem(e: InventoryDragEvent) {
        if (Data.isGameStart) e.isCancelled = true
    }

    @EventHandler
    fun onDropItem(e: PlayerDropItemEvent) {
        if (Data.isGameStart) e.isCancelled = true
    }
}