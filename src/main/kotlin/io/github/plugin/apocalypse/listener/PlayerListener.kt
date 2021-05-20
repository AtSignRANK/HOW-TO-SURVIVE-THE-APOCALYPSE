package io.github.plugin.apocalypse.listener

import io.github.plugin.apocalypse.Main
import org.bukkit.Bukkit
import org.bukkit.entity.Creeper
import org.bukkit.entity.Enderman
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class PlayerListener(private val plugin: Main): Listener {

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val deathamount = this.plugin.serverconfig?.get(event.player.uniqueId.toString() + ".Death") as Int
        this.plugin.serverconfig?.set(event.player.uniqueId.toString() + ".Death", (deathamount + 1))
        this.plugin.saveConfig()
        Bukkit.getScheduler().runTaskLater(this.plugin, Runnable {
            event.player.teleport(this.plugin.randomLocation())
        }, 5L)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if(this.plugin.serverconfig!!.isSet(event.player.uniqueId.toString())) return
        event.player.teleport(this.plugin.randomLocation())
        this.plugin.serverconfig!!.set(event.player.uniqueId.toString() + ".Death", 0)
        this.plugin.saveConfig()
    }

    @EventHandler
    fun strongMonsterSpawnEvent(event: EntitySpawnEvent) {
        val entity = event.entity
        if(!this.plugin.MONSTER) return
        if(entity.type == EntityType.ZOMBIE) {
            val zombie = entity as Zombie
            zombie.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 2, true, false))
            zombie.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, Int.MAX_VALUE, 1, true, false))
            zombie.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 2, true, true))
            zombie.maxHealth = this.plugin.ZOMBIE_HEALTH
        } else if(entity.type == EntityType.CREEPER) {
            val creeper = entity as Creeper
            creeper.explosionRadius = 30
            creeper.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 4, true, true))
            creeper.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 2, true, true))
            creeper.maxHealth = this.plugin.CREEPER_HEALTH
        } else if(entity.type == EntityType.ENDERMAN) {
            val enderman = entity as Enderman
            enderman.addPotionEffect(PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 3, true, true))
            enderman.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, Int.MAX_VALUE, 0, true, true))
        }
    }
}