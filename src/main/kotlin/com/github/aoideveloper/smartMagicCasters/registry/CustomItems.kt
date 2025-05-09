package com.github.aoideveloper.smartMagicCasters.registry

import com.github.aoideveloper.smartMagicCasters.item.CustomItem
import com.github.aoideveloper.smartMagicCasters.item.MagicalWand
import net.kyori.adventure.text.Component
import org.bukkit.Material

object CustomItems {
  val registeredCustomItems = mutableMapOf<String, CustomItem>()
  val magicalWand = MagicalWand("magical_wand", Component.text("Magical Wand"), Material.FISHING_ROD).register()

  fun CustomItem.register() = also {
    registeredCustomItems.put(it.itemName, it)
  }
}
