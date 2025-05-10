package com.github.aoideveloper.smartMagicCasters.item

import com.github.aoideveloper.smartMagicCasters.item.CustomItem.Companion.itemName
import com.github.aoideveloper.smartMagicCasters.registry.CustomItems
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object CustomItemTicker {
  private val activeMap: MutableMap<Player, MutableSet<InteractiveItem>> = mutableMapOf()

  private fun lifecycle() {
    activeMap.filterKeys { !it.isOnline }.forEach { activeMap.remove(it.key) }
  }

  private fun handleTick(player: Player, item: InteractiveItem) {
    val currentlyActive = item.activationCondition.isActive(player, item)
    val wasActive = activeMap[player]?.contains(item) == true

    when {
      currentlyActive && !wasActive -> {
        activeMap.getOrPut(player) { mutableSetOf() }.add(item)
        item.onActivated(player)
      }
      currentlyActive && wasActive -> {
        item.onActive(player)
      }
      !currentlyActive && wasActive -> {
        activeMap[player]?.remove(item)
        item.onDeactivated(player)
      }
    }
  }

  fun onTick() {
    for (player in Bukkit.getOnlinePlayers()) {
      for (slot in player.inventory.contents) {
        val item = CustomItems.registeredCustomItems[slot?.itemName ?: continue] ?: continue
        if (item is InteractiveItem) {
          handleTick(player, item)
        }
      }
    }
    lifecycle()
  }
}
