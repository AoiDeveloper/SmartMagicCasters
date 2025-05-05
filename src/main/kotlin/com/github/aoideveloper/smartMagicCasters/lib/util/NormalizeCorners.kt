package com.github.aoideveloper.smartMagicCasters.lib.util

import org.bukkit.Location

fun normalizeCorners(loc1: Location, loc2: Location): Pair<Location, Location> {
  require(loc1.world == loc2.world) {
    "Locations must be in the same world: ${loc1.world?.name} vs. ${loc2.world?.name}"
  }

  val world = loc1.world!!
  val min = Location(world, minOf(loc1.x, loc2.x), minOf(loc1.y, loc2.y), minOf(loc1.z, loc2.z))
  val max = Location(world, maxOf(loc1.x, loc2.x), maxOf(loc1.y, loc2.y), maxOf(loc1.z, loc2.z))
  return min to max
}
