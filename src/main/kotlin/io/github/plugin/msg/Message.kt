package io.github.plugin.msg

import io.github.plugin.msg.command.Command
import io.github.plugin.msg.command.CommandTabCompleter
import org.bukkit.ChatColor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Message: JavaPlugin(), Listener {
    override fun onEnable() {
        server.pluginManager.also { spm ->
            spm.registerEvents(this, this)
        }
        getCommand("귓").also { command ->
            command?.setExecutor(Command(this))
        }
        getCommand("칭호").also { command ->
            command?.setExecutor(Command(this))
            command?.tabCompleter = CommandTabCompleter()
        }
    }

    fun returnColor(colorcode: Char): ChatColor? {
        when(colorcode) {
            '0' -> return ChatColor.BLACK
            '1' -> return ChatColor.DARK_BLUE
            '2' -> return ChatColor.DARK_GREEN
            '3' -> return ChatColor.DARK_AQUA
            '4' -> return ChatColor.DARK_RED
            '5' -> return ChatColor.DARK_PURPLE
            '6' -> return ChatColor.GOLD
            '7' -> return ChatColor.GRAY
            '8' -> return ChatColor.DARK_GRAY
            '9' -> return ChatColor.BLUE
            'a' -> return ChatColor.GREEN
            'b' -> return ChatColor.AQUA
            'c' -> return ChatColor.RED
            'd' -> return ChatColor.LIGHT_PURPLE
            'e' -> return ChatColor.YELLOW
            'f' -> return ChatColor.WHITE
            else -> return null
        }
    }
}