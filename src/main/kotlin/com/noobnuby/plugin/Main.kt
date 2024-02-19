package com.noobnuby.plugin

import com.noobnuby.plugin.commands.Star
import org.bukkit.plugin.java.JavaPlugin
import xyz.icetang.lib.kommand.kommand
import java.io.File

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

        }
    }
}