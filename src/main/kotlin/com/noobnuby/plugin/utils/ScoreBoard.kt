package com.noobnuby.plugin.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot

object ScoreBoard {
    val scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    var objective = scoreboard.registerNewObjective("star", Criteria.DUMMY, Component.text("POINT",NamedTextColor.YELLOW))

    fun showScore(p: Player) {
        objective.displaySlot = DisplaySlot.SIDEBAR
        p.scoreboard = scoreboard
    }

    fun resetScoreboard() {
        objective.unregister()
        objective = scoreboard.registerNewObjective("star", Criteria.DUMMY, Component.text("POINT", NamedTextColor.YELLOW))
    }
}