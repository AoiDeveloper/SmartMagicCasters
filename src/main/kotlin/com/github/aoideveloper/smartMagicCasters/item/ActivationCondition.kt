package com.github.aoideveloper.smartMagicCasters.item

import com.github.aoideveloper.smartMagicCasters.item.CustomItem.Companion.itemName
import org.bukkit.entity.Player

fun interface ActivationCondition {
    fun isActive(player: Player, item: CustomItem): Boolean

    companion object {
        val NONE = ActivationCondition { _, _ -> false }
        val HAND_HELD = ActivationCondition { player, item ->
            item.itemName == player.inventory.itemInMainHand.itemName
        }
        val IN_INVENTORY = ActivationCondition { player, item ->
            player.inventory.contents.any { item.itemName == player.inventory.itemInMainHand.itemName }
        }
    }
}
