package com.github.aoideveloper.smartMagicCasters.registry

import com.github.aoideveloper.smartMagicCasters.SmartMagicCasters
import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin

object NamespacedKeys {
  val plugin: Plugin by lazy { SmartMagicCasters.plugin }

  val item = NamespacedKey(plugin, "item")
}
