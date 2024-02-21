package com.noobnuby.plugin

import com.noobnuby.plugin.commands.Star
import com.noobnuby.plugin.events.*
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand

class Main : JavaPlugin() {
    companion object { lateinit var instance: Main }

    override fun onEnable() {
        instance = this
        saveResource("config.yml",false)

        logger.info("Enable Plugin!")

        kommand {
            Star.register(this)
        }

        server.pluginManager.apply {
            registerEvents(JoinEvent(),this@Main)
            registerEvents(PlaceBreakEvent(),this@Main)
            registerEvents(DamageEvent(),this@Main)
            registerEvents(DathEvent(),this@Main)
            registerEvents(RespawnEvent(),this@Main)
            registerEvents(SwapItemEvent(),this@Main)
            registerEvents(ItemEvent(),this@Main)
            registerEvents(FoodEvent(),this@Main)
        }

        setGameRule()
    }

    fun setGameRule() {
        Bukkit.getWorlds().forEach {
            it.time = 1000
            it.setStorm(false)

            it.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS,false)
            it.setGameRule(GameRule.DO_DAYLIGHT_CYCLE,false)
            it.setGameRule(GameRule.DO_WEATHER_CYCLE,false)
            it.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,true)
            it.setGameRule(GameRule.KEEP_INVENTORY,true)
        }
    }
}