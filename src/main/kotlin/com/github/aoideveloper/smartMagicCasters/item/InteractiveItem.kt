package com.github.aoideveloper.smartMagicCasters.item

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player

open class InteractiveItem(
  itemName: String,
  displayName: Component,
  displayMaterial: Material,
  val activationCondition: ActivationCondition = ActivationCondition.NONE,
  val actions: MutableMap<InteractionType, (Player) -> Unit> = mutableMapOf(),
) : CustomItem(itemName, displayName, displayMaterial) {
  open fun onActivated(player: Player) {}

  open fun onDeactivated(player: Player) {}

  open fun onActive(player: Player) {}
}

enum class InteractionType {
  RIGHT_CLICK,
  LEFT_CLICK,
  START_SNEAKING,
  END_SNEAKING,
}
