package com.noobnuby.plugin.utils

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.regions.Region
import org.bukkit.entity.Player

val Player.selection: Region?
    get() {
        return try {
            WorldEdit.getInstance().sessionManager[BukkitAdapter.adapt(this)]?.run {
                getSelection(selectionWorld)
            }
        } catch (e: Exception) {
            null
        }
    }