package io.github.plugin.apocalypse.listener

import io.github.plugin.apocalypse.Main
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class MotdListener(private val plugin: Main): Listener {
    @EventHandler
    fun setServerMotd(event: ServerListPingEvent) {
        event.motd(Component.text("${ChatColor.RED}아포칼립스에서 살아남는 방법 | How to survive the Apocalypse"))
    }
}