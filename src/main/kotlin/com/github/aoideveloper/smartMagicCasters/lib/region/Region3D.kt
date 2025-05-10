package com.github.aoideveloper.smartMagicCasters.lib.region

import com.github.aoideveloper.smartMagicCasters.lib.filter.VisualizableFilter
import org.bukkit.Location
import org.bukkit.entity.LivingEntity

interface Region3D : VisualizableFilter<Location, LivingEntity> {
  fun contains(location: Location): Boolean
}
