package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main
import com.noobnuby.plugin.utils.selection
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location
import org.bukkit.Sound
import xyz.icetang.lib.kommand.PluginKommand
import xyz.icetang.lib.kommand.getValue

object Star {
    fun register(kommand: PluginKommand) {
        kommand.register("star") {
            requires { isPlayer && isOp }
            then("start") {
                executes {
                    val config = Main.instance.config
                    player.sendMessage("${config.getLocation("map.firstLocation")}")
                }
            }
            then("save") {
                then("name" to string()) {
                    executes {
                        val name: String by it
                        val selection = player.selection
                        val config = Main.instance.config
                        if (selection == null) {
                            player.sendMessage(Component.text("나무 도끼로 구역을 선택해주세요!").color(NamedTextColor.RED))
                            player.playSound(player.location,Sound.ENTITY_ENDERMAN_TELEPORT,0.5f,1.0f)
                            return@executes
                        }
                        if (config.contains(name)) {
                            player.sendMessage(Component.text("이미 같은 이름의 맵이 저장되어있습니다").color(NamedTextColor.RED))
                            player.playSound(player.location,Sound.ENTITY_ENDERMAN_TELEPORT,0.5f,1.0f)
                            return@executes
                        }

                        val minimumLocation = Location(player.world,selection.minimumPoint.x.toDouble(),selection.minimumPoint.y.toDouble(),selection.minimumPoint.z.toDouble())
                        val maximumLocation = Location(player.world,selection.maximumPoint.x.toDouble(),selection.maximumPoint.y.toDouble(),selection.maximumPoint.z.toDouble())
                        config.set("${name}.firstLocation", minimumLocation)
                        config.set("${name}.secondLocation", maximumLocation)
                        Main.instance.saveConfig()
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>성공적으로 <yellow>${name}<green>을 저장하였습니다!"))
                        player.playSound(player.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.5f,1.0f)
                    }
                }
            }
        }
    }
}