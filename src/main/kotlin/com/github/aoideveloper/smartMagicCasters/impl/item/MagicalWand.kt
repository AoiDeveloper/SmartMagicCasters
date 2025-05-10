package com.github.aoideveloper.smartMagicCasters.impl.item

import com.github.aoideveloper.smartMagicCasters.item.ActivationCondition
import com.github.aoideveloper.smartMagicCasters.item.InteractionType
import com.github.aoideveloper.smartMagicCasters.item.InteractiveItem
import com.github.aoideveloper.smartMagicCasters.lib.region.RegionCuboid
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class MagicalWand(itemName: String, displayName: Component, displayMaterial: Material) :
  InteractiveItem(
    itemName,
    displayName,
    displayMaterial,
    activationCondition = ActivationCondition.Companion.HAND_HELD,
    actions = actions,
  ) {
  companion object {
    val actions =
      mutableMapOf<InteractionType, (Player) -> Unit>(InteractionType.RIGHT_CLICK to { player ->
        player.sendMessage("魔法の杖が発動しました！")
        player.addPotionEffect(PotionEffect(PotionEffectType.GLOWING, 200, 1))
      })
  }

  override fun onActivated(player: Player) {
    player.sendMessage("魔法の杖が有効化されました！")
  }

  override fun onActive(player: Player) {
    val location = player.location
    val affectArea = RegionCuboid.fromCorners(
      location.clone().add(-4.0, -4.0, -4.0),
      location.clone().add(4.0, 4.0, 4.0)
    )
    val visualizeHandle = affectArea.visualize()
    visualizeHandle.invoke(arrayOf(player))
  }
}