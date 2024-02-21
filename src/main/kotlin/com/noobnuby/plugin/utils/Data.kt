package com.noobnuby.plugin.utils

import it.unimi.dsi.fastutil.Hash
import org.bukkit.Location
import org.bukkit.entity.Player

object Data {
    var Map:String? = null
    var isGameStart = false
    val PlayerLastLoc:HashMap<Player,Location> = hashMapOf()
    var NetherStar:HashMap<String,Player> = hashMapOf()
}