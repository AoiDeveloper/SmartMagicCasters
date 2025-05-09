package com.github.aoideveloper.smartMagicCasters.registry

import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player

object CustomCommands {
  val registeredCommands = mutableMapOf<String, CommandExecutor>()

  val debugCommand =
    CommandExecutor { sender, command, label, args ->
        (sender as? Player)?.let {
          sender.inventory.addItem(CustomItems.magicalWand.create())
          true
        } ?: false
      }
      .registerAs("debug")

  private fun CommandExecutor.registerAs(commandName: String): CommandExecutor = also {
    registeredCommands[commandName] = this
  }
}
