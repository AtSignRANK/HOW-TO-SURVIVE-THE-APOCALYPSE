package io.github.plugin.apocalypse

import io.github.plugin.apocalypse.command.Command
import io.github.plugin.apocalypse.command.CommandTabCompleter
import io.github.plugin.apocalypse.listener.InteractionListener
import io.github.plugin.apocalypse.listener.MotdListener
import io.github.plugin.apocalypse.listener.PlayerListener
import org.bukkit.*
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.plugin.java.JavaPlugin

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.io.File
import java.io.IOException
import kotlin.random.Random

class Main: JavaPlugin() {
    val WEAPON = ItemStack(Material.GOLDEN_HOE)
    val WEAPON_META = WEAPON.itemMeta

    val WORLD_SIZE = 1024

    val ZOMBIE_HEALTH = 40.0
    val CREEPER_HEALTH = 100.0

    val OVERWORLD = Bukkit.getWorld("world")
    val NETHERWORLD = Bukkit.getWorld("world_nether")
    val ENDERWORLD = Bukkit.getWorld("world_the_end")

    var serverfile = File("plugins/APOCALYPSE/config.yml")
    var serverconfig: FileConfiguration? = null

    var MONSTER = false

    fun loadConfig() {
        serverconfig = YamlConfiguration.loadConfiguration(serverfile)
        try {
            if (!serverfile.exists()) {
                (serverconfig as @NotNull YamlConfiguration).save(serverfile)
            }
            (serverconfig as @NotNull YamlConfiguration).load(serverfile)
        } catch (localException: Exception) {
            localException.printStackTrace()
        }
    }

    override fun saveConfig() {
        try {
            serverconfig!!.save(serverfile)
        } catch (E: IOException) {
            E.printStackTrace()
        }
    }

    override fun onEnable() {
        server.pluginManager.also { serverextends ->
            serverextends.registerEvents(PlayerListener(this), this)
            serverextends.registerEvents(InteractionListener(this), this)
            serverextends.registerEvents(MotdListener(this), this)
        }
        loadConfig()
        WEAPON_META.setDisplayName("${ChatColor.AQUA}VACCINE")
        WEAPON.setItemMeta(WEAPON_META)
        getCommand("vaccine")?.setExecutor(Command(this))
        getCommand("monster")?.also{ monster ->
            monster.setExecutor(Command(this))
            monster.tabCompleter = CommandTabCompleter()
        }
        OVERWORLD?.worldBorder?.setCenter(0.0, 0.0)
        OVERWORLD?.worldBorder?.setSize(2 * WORLD_SIZE.toDouble(), 0)
        NETHERWORLD?.worldBorder?.setCenter(0.0, 0.0)
        NETHERWORLD?.worldBorder?.setSize(2 * (WORLD_SIZE / 4).toDouble(), 0)
        OVERWORLD?.setGameRule(GameRule.REDUCED_DEBUG_INFO, true)
        OVERWORLD?.setGameRule(GameRule.NATURAL_REGENERATION, false)
        OVERWORLD?.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
        NETHERWORLD?.setGameRule(GameRule.REDUCED_DEBUG_INFO, true)
        NETHERWORLD?.setGameRule(GameRule.NATURAL_REGENERATION, false)
        NETHERWORLD?.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
        ENDERWORLD?.setGameRule(GameRule.REDUCED_DEBUG_INFO, true)
        ENDERWORLD?.setGameRule(GameRule.NATURAL_REGENERATION, false)
        ENDERWORLD?.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
    }

    fun randomLocation(): Location {
        val random = Random
        return Location(OVERWORLD, (random.nextInt() % WORLD_SIZE).toDouble(), 0.0, (random.nextInt() % WORLD_SIZE).toDouble()).toHighestLocation()
    }

    fun damageWeapon(weaponstack: ItemStack): ItemStack {
        val random = Random
        val weaponmeta = weaponstack.itemMeta as Damageable
        weaponmeta.damage = weaponmeta.damage + 4
        if(weaponmeta.damage >= 32) return ItemStack(Material.GOLD_NUGGET, random.nextInt() % 9)
        weaponstack.setItemMeta(weaponmeta as @Nullable ItemMeta)
        return weaponstack
    }

    fun dieExplode(location: Location) {
        location.createExplosion(10.toFloat())
    }

    fun noDamageSimilar(itemstack1: ItemStack,  itemstack2: ItemStack): Boolean {
        val its1 = itemstack1.itemMeta as Damageable
        val its2 = itemstack2.itemMeta as Damageable
        its1.damage = 1
        its2.damage = 1
        return its1 == its2
    }
}