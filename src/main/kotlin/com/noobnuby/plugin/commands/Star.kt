package com.noobnuby.plugin.commands

import com.noobnuby.plugin.Main
import com.noobnuby.plugin.handler.Sheduler
import com.noobnuby.plugin.utils.Data
import com.noobnuby.plugin.utils.ScoreBoard
import com.noobnuby.plugin.utils.selection
import com.sk89q.minecraft.util.commands.CommandContext
import com.sk89q.worldedit.regions.Region
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import xyz.icetang.lib.kommand.KommandArgument.Companion.dynamic
import xyz.icetang.lib.kommand.KommandContext
import xyz.icetang.lib.kommand.PluginKommand
import xyz.icetang.lib.kommand.getValue
import kotlin.random.Random

object Star {
    fun register(kommand: PluginKommand) {
        kommand.register("star") {
            requires { isPlayer && isOp }
            then("start") {
                executes {
                    val config = Main.instance.config
                    Data.isGameStart = true
                    if (Data.Map == null) {
                        val map = config.getKeys(false).filter { it != "lobby" }.random()
                        Data.Map = map
                    }

                    val firLoc = config.getLocation("${Data.Map}.firstLocation")!!
                    val secLoc = config.getLocation("${Data.Map}.secondLocation")!!

                    Bukkit.getOnlinePlayers().forEach {
                        it.inventory.clear()
                        it.gameMode = GameMode.SURVIVAL
                        it.health = 20.0

                        val randomX: Double
                        val randomZ: Double

                        if (firLoc.x < secLoc.x) {
                            randomX = Random.nextDouble(firLoc.x + 1.5, secLoc.x - 1.5)
                            randomZ = Random.nextDouble(firLoc.z + 1.5, secLoc.z - 1.5)
                        }
                        else {
                            randomX = Random.nextDouble(secLoc.x - 1.5, firLoc.x + 1.5)
                            randomZ = Random.nextDouble(secLoc.z - 1.5, firLoc.z + 1.5)
                        }

                        val highestY = firLoc.world.getHighestBlockYAt(randomX.toInt(), randomZ.toInt()).toDouble()

                        it.teleport(Location(firLoc.world, randomX, highestY + 1, randomZ))
                        it.inventory.setItem(0, ItemStack(Material.WOODEN_SWORD).apply { itemMeta = itemMeta.apply { isUnbreakable = true } })

                        ScoreBoard.showScore(it)
                    }
                    val players = Bukkit.getOnlinePlayers().toList()
                    val randomP = players[Random.nextInt(players.size)]

                    randomP.inventory.setItemInOffHand(ItemStack(Material.NETHER_STAR))
                    Data.NetherStar.set("Star",randomP)

                    Sheduler.Start()
                }
            }
            then("save") {
                then("map") {
                    then("name" to string()) {
                        executes {
                            val name: String by it
                            val selection = player.selection
                            val config = Main.instance.config
                            if (selection == null) {
                                player.sendMessage(Component.text("나무 도끼로 구역을 선택해주세요!").color(NamedTextColor.RED))
                                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f)
                                return@executes
                            }
                            if (config.contains(name)) {
                                player.sendMessage(
                                    Component.text("이미 같은 이름의 맵이 저장되어있습니다\n", NamedTextColor.RED).append(
                                        Component.text("맵을 덮어쓰고 싶으시다면 뒤에 confirm을 붙여주세요", NamedTextColor.YELLOW)
                                    )
                                )
                                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f)
                                return@executes
                            }

                            saveLocation(name, player, selection)
                        }
                        then("confirm") {
                            executes {
                                val name: String by it
                                val selection = player.selection
                                if (selection == null) {
                                    player.sendMessage(Component.text("나무 도끼로 구역을 선택해주세요!").color(NamedTextColor.RED))
                                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f)
                                    return@executes
                                }
                                saveLocation(name, player, selection)
                            }
                        }
                    }
                }
                then("lobby") {
                    executes {
                        val config = Main.instance.config
                        config.set("lobby", Location(player.location.world, player.location.x, player.location.y, player.location.z, 0.0f, 0.0f))
                        player.sendMessage(Component.text("로비위치를 저장하였습니다!").color(NamedTextColor.GREEN))
                        player.playSound(player.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.5f,1.0f)
                        Main.instance.saveConfig()
                    }
                }
            }
            then("remove") {
                then("removeMapName" to mapNamesArgument()) {
                    executes {
                        val removeMapName: String by it
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>성공적으로 <yellow>${removeMapName}<green>을 삭제하였습니다."))
                        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.0f)
                        Main.instance.config.set(removeMapName, null)
                        Main.instance.saveConfig()
                    }
                }
            }
            then("setting") {
                then("mapName" to mapNamesArgument()) {
                    executes {
                        val mapName: String by it
                        Data.Map = mapName
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>맵을 <yellow>${mapName}<green>으로 설정하였습니다."))
                        player.playSound(player.location,Sound.ENTITY_PLAYER_LEVELUP,0.5f,1.0f)
                    }
                }
            }
        }
    }


    fun mapNamesArgument() = dynamic { _: KommandContext, input: String ->
        val config = Main.instance.config
        val mapNames = config.getKeys(false).filter { it != "lobby" }
        mapNames.find { it == input }
    }.apply {
        suggests {
            suggest(Main.instance.config.getKeys(false).filter { it != "lobby" })
        }
    }


    fun saveLocation(name: String, player: Player, selection: Region) {
        val config = Main.instance.config

        val minimumLocation = Location(player.world,selection.minimumPoint.x.toDouble(),selection.minimumPoint.y.toDouble(),selection.minimumPoint.z.toDouble())
        val maximumLocation = Location(player.world,selection.maximumPoint.x.toDouble(),selection.maximumPoint.y.toDouble(),selection.maximumPoint.z.toDouble())
        config.set("${name}.firstLocation", minimumLocation)
        config.set("${name}.secondLocation", maximumLocation)
        Main.instance.saveConfig()
        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>성공적으로 <yellow>${name}<green>을 저장하였습니다!"))
        player.playSound(player.location,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,0.5f,1.0f)
    }
}