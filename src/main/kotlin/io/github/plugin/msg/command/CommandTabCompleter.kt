package io.github.plugin.msg.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandTabCompleter: TabCompleter{
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val commandList = mutableListOf<String>()

        if (command.name == "칭호") {
            when (args.size) {
                1 -> {
                    commandList.add("설정")
                    return commandList
                }
            }
        }
        return null
    }
}