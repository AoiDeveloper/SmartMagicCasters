package com.github.aoideveloper.smartMagicCasters.lib.region

import org.bukkit.Location
import org.bukkit.entity.Player

class RegionSphere private constructor(var center: Location, private var radiusSquared: Double) :
  Region3D {

  companion object {
    fun fromRadius(center: Location, radius: Double): RegionSphere {
      return RegionSphere(center, radius * radius)
    }

    fun fromRadiusSquared(center: Location, radiusSquared: Double): RegionSphere {
      return RegionSphere(center, radiusSquared)
    }
  }

  override fun contains(player: Player): Boolean {
    return player.location.distanceSquared(center) <= radiusSquared
  }
}
