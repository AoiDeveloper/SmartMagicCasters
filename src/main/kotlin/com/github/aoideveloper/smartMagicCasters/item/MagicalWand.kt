package com.github.aoideveloper.smartMagicCasters.item

import com.github.aoideveloper.smartMagicCasters.lib.region.RegionSphere
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player

class MagicalWand(
    itemName: String,
    displayName: Component,
    displayMaterial: Material,
) : InteractiveItem(itemName, displayName, displayMaterial, activationCondition = ActivationCondition.HAND_HELD, actions = actions) {
    companion object {
        val actions = mutableMapOf<InteractionType, (Player) -> Unit>(
            InteractionType.RIGHT_CLICK to { player ->
            }
        )
    }

    override fun onActivated(player: Player) {
    }

    override fun onActive(player: Player) {
        val affectArea = RegionSphere.fromRadius(player.location, 8.0)
        val visualizeHandle = affectArea.visualize()
        visualizeHandle.invoke(arrayOf(player))
    }
}