package io.github.plugin.apocalypse.command

import io.github.plugin.apocalypse.Main
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Command(private val plugin: Main): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(command.name == "vaccine") {
            val player = sender as Player
            player.inventory.addItem(this.plugin.WEAPON)
        } else if(command.name == "monster") {
            if(args.size == 0) sender.sendMessage("${ChatColor.YELLOW}${this.plugin.MONSTER}")
            else if(args[0] == "true") this.plugin.MONSTER = true
            else if(args[0] == "false") this.plugin.MONSTER = false
            else sender.sendMessage("${ChatColor.YELLOW}${args[0]} 이라는 인수는 존재하지 않습니다")
        }
        return false
    }
}