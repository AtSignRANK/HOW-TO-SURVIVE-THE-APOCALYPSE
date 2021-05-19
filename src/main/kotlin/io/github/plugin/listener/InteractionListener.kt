package io.github.plugin.listener

import io.github.plugin.Main
import net.kyori.adventure.text.Component
import org.bukkit.*
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.EquipmentSlot

class InteractionListener(private val plugin: Main): Listener {

    @EventHandler
    fun useWeapon(event: PlayerInteractAtEntityEvent) {
        val victim = event.rightClicked
        val player = event.player
        if(this.plugin.noDamageSimilar(player.inventory.itemInMainHand, this.plugin.WEAPON) && event.hand == EquipmentSlot.HAND) {
            if(victim.type == EntityType.ZOMBIE) {
                val weaponstack = this.plugin.damageWeapon(player.inventory.itemInMainHand)
                val victimzombie = victim as Zombie
                player.inventory.setItemInMainHand(weaponstack)
                victimzombie.health = 0.0
            }
        }
    }

    @EventHandler
    fun useSpaceShip(event: BlockPlaceEvent) {
        if(event.blockPlaced.type == Material.BEDROCK) {
            Bukkit.getServer().sendMessage(Component.text("${ChatColor.YELLOW}${event.player.name} 님이 아포칼립스 세상에서 벗어났습니다!"))
            event.isCancelled = true
            event.player.gameMode = GameMode.SPECTATOR
            event.player.playSound(event.player.location, Sound.ITEM_TOTEM_USE, 1.toFloat(), 1.toFloat())
        }
    }
}