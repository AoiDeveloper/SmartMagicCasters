package com.github.aoideveloper.smartMagicCasters.item

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
                player.sendMessage("Hi!Bro!")
            }
        )
    }

    override fun onActivated(player: Player) {
        player.sendMessage("Ready for Casting!")
        println("here")
    }
}