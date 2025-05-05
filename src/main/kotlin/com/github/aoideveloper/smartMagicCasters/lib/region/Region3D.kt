package com.github.aoideveloper.smartMagicCasters.lib.region

import org.bukkit.entity.Player

interface Region3D {
  fun contains(player: Player): Boolean
}
