package io.github.plugin.apocalypse.command

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class CommandTabCompleter: TabCompleter {

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        val commandList = mutableListOf<String>()

        if (command.name == "monster") {
            when (args.size) {
                1 -> {
                    commandList.add("true")
                    commandList.add("false")

                    return commandList
                }
            }
        }
        return null
    }
}