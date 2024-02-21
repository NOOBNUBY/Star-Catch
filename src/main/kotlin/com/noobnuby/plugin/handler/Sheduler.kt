package com.noobnuby.plugin.handler

import com.noobnuby.plugin.Main
import com.noobnuby.plugin.utils.Data
import com.noobnuby.plugin.utils.ScoreBoard
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Sound
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Score
import java.time.Duration

object Sheduler {
    var taskId:Int? = null

    fun Start() {
        taskId = Bukkit.getScheduler().runTaskTimer(Main.instance, Runnable {
            Bukkit.getOnlinePlayers().forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION,PotionEffect.INFINITE_DURATION,1,true,true))
                if (Data.NetherStar["Star"] == it) {
                    it.addPotionEffect(PotionEffect(PotionEffectType.GLOWING,PotionEffect.INFINITE_DURATION,5,true,true))
                    it.sendActionBar(Component.text("네더의 별을 소유하고 있습니다!",NamedTextColor.AQUA))
                    ScoreBoard.objective.getScore(it.name).score += 1
                    if (ScoreBoard.objective.getScore(it.name).score >= 100) {
                        val winner = it
                        Data.NetherStar.remove("Star")
                        Bukkit.getOnlinePlayers().forEach {
                            it.showTitle(Title.title(Component.text("게임 종료!",NamedTextColor.GOLD), Component.text("우승자: ${winner.name}"),
                                Title.Times.times(Duration.ZERO, Duration.ofSeconds(7), Duration.ofMillis(500))
                            ))
                            it.playSound(it.location,Sound.UI_TOAST_CHALLENGE_COMPLETE,0.5f,1.0f)
                            it.sendMessage(Component.text("우승자: ${winner.name}",NamedTextColor.YELLOW))
                            it.inventory.clear()
                            it.removePotionEffect(PotionEffectType.GLOWING)
                            it.removePotionEffect(PotionEffectType.REGENERATION)
                            if (Main.instance.config.contains("lobby")) {
                                it.teleport(Main.instance.config.getLocation("lobby")!!)
                            }
                            it.scoreboard = Bukkit.getScoreboardManager().newScoreboard
                        }
                        ScoreBoard.resetScoreboard()
                        Data.isGameStart = false
                        stopSchedule()
                    }
                }
                else {
                    it.removePotionEffect(PotionEffectType.GLOWING)
                }
            }
        },0,20).taskId
    }

    fun stopSchedule() {
        if (taskId != null) {
            Bukkit.getScheduler().cancelTask(taskId!!)

            taskId = null
        }
    }
}