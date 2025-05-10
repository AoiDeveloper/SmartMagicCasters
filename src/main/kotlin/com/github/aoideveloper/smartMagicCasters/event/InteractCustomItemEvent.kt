package com.github.aoideveloper.smartMagicCasters.event

import com.github.aoideveloper.smartMagicCasters.item.CustomItem.Companion.itemName
import com.github.aoideveloper.smartMagicCasters.item.InteractionType
import com.github.aoideveloper.smartMagicCasters.item.InteractiveItem
import com.github.aoideveloper.smartMagicCasters.registry.CustomItems
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object InteractCustomItemEvent: Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val item = CustomItems.registeredCustomItems[event.item?.itemName] ?: return
        (item as? InteractiveItem)?.run {
            item.actions[when(event.action) {
                Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> InteractionType.LEFT_CLICK
                Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> InteractionType.RIGHT_CLICK
                else -> null
            }]?.run {
                event.isCancelled = true
                this.invoke(event.player)
            }
        }
    }
}