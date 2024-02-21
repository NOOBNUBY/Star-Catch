package com.noobnuby.plugin.events

import com.noobnuby.plugin.utils.Data
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class DathEvent: Listener {
    @EventHandler
    fun onDeath(e:PlayerDeathEvent) {
        e.deathMessage(Component.text(""))
        if(!Data.isGameStart) return
        val p = e.player
        Data.PlayerLastLoc[p] = p.location

        if (Data.NetherStar.get("Star") == p) {
            val killer = e.entity.killer as Player
            p.inventory.setItemInOffHand(ItemStack(Material.AIR))
            killer.inventory.setItemInOffHand(ItemStack(Material.NETHER_STAR))

            Data.NetherStar.set("Star",killer)
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<yellow>${killer.name}<aqua>님이 별을 소지하고 있습니다!"))
        }
    }
}