package com.github.aoideveloper.smartMagicCasters.lib.region

import com.destroystokyo.paper.ParticleBuilder
import com.github.aoideveloper.smartMagicCasters.SmartMagicCasters
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

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

  override fun contains(location: Location): Boolean {
    return location.distanceSquared(center) <= radiusSquared
  }

  override fun visualize(): (Array<Player>) -> Unit {
    return { players ->
      val time = SmartMagicCasters.plugin.server.currentTick.toDouble()
      val radius = sqrt(radiusSquared)
      val particleCount = 5

      fun drawRing(offset: Double, axis: String) {
        for (i in 0 until particleCount) {
          val angle = (time * 0.1 + i * (2 * Math.PI / particleCount) + offset) % (2 * Math.PI)

          val (x, y, z) = when (axis) {
            "horizontal" -> Triple(
              radius * cos(angle),
              0.0,
              radius * sin(angle)
            )
            "verticalX" -> Triple(
              0.0,
              radius * cos(angle),
              radius * sin(angle)
            )
            "diagonalXZ" -> {
              // 斜め回転：軸ベクトル(1,1,0)に垂直な円
              val sinA = sin(angle)
              val cosA = cos(angle)
              val sqrt2 = sqrt(2.0)
              val x = (cosA - sinA) / sqrt2 * radius
              val y = (cosA + sinA) / sqrt2 * radius
              val z = 0.0
              Triple(x, y, z)
            }
            else -> Triple(0.0, 0.0, 0.0)
          }

          val loc = center.clone().add(x, y, z)

          ParticleBuilder(Particle.CLOUD)
            .extra(0.0)
            .location(loc)
            .receivers(*players)
            .spawn()
        }
      }

      // 各軸のリングを描く（offsetを少しずらして個性を出す）
      drawRing(0.0, "horizontal")   // 水平リング（Y軸中心）
      drawRing(Math.PI / 5, "verticalX") // 縦リング（X軸中心）
      drawRing(Math.PI / 2, "diagonalXZ") // 斜めリング（X+Y軸に垂直）
    }
  }

  override fun filter(): Collection<LivingEntity> {
    return center.getNearbyLivingEntities(sqrt(radiusSquared))
  }
}
