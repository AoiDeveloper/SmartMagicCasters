package com.github.aoideveloper.smartMagicCasters.item

import com.github.aoideveloper.smartMagicCasters.registry.NamespacedKeys
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

typealias ItemStackCreator = (CustomItem) -> ItemStack

abstract class CustomItem(
  val itemName: String,
  val displayName: Component = Component.text(itemName),
  val displayMaterial: Material = Material.BARRIER,
  private val creator: ItemStackCreator = defaultCreator,
  ) {
  fun create(): ItemStack {
    return creator(this)
  }

  companion object {
    private val defaultCreator: ItemStackCreator = { item ->
      ItemStack(item.displayMaterial).apply {
        editMeta {
          it.displayName(item.displayName)
          it.persistentDataContainer.set<String, String>(
            NamespacedKeys.item,
            PersistentDataType.STRING,
            item.itemName,
          )
        }
      }
    }

    val ItemStack.itemName: String?
      get() = this.itemMeta?.persistentDataContainer?.get(NamespacedKeys.item, PersistentDataType.STRING)
  }
}
