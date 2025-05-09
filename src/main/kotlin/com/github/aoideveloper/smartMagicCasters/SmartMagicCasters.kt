package com.github.aoideveloper.smartMagicCasters

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import com.github.aoideveloper.smartMagicCasters.item.CustomItemTicker
import com.github.aoideveloper.smartMagicCasters.registry.CustomCommands
import com.github.aoideveloper.smartMagicCasters.registry.CustomItems
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.java.JavaPlugin

class SmartMagicCasters : JavaPlugin() {
  companion object {
    lateinit var plugin: JavaPlugin
  }
  override fun onEnable() {
    // Plugin startup logic
    plugin = this

    println(CustomItems.registeredCustomItems)

    CustomCommands.registeredCommands.forEach { getCommand(it.key)?.setExecutor(it.value) }
    server.pluginManager.registerEvents(object: Listener {
      @EventHandler
      fun onTick(e: ServerTickStartEvent) {
        CustomItemTicker.onTick()
      }
    }, this)
  }

  override fun onDisable() {
    // Plugin shutdown logic
  }
}
