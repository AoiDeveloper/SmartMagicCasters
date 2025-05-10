package com.github.aoideveloper.smartMagicCasters.lib.region

import com.destroystokyo.paper.ParticleBuilder
import com.github.aoideveloper.smartMagicCasters.SmartMagicCasters
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import kotlin.math.abs
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

      val averageDirection = players
        .map { it.location.direction }
        .fold(Vector(0, 0, 0)) { acc, vec -> acc.add(vec) }
        .normalize()

      val lookDir = averageDirection.clone().setY(0.0).normalize()
      val perpendicular = Vector(-lookDir.z, 0.0, lookDir.x).normalize()

      fun drawRing(offset: Double, axis: String) {
        for (i in 0 until particleCount) {
          val angle = (time * 0.1 + i * (2 * Math.PI / particleCount) + offset) % (2 * Math.PI)
          val (x, y, z) = when (axis) {
            "horizontal" -> Triple(radius * cos(angle), 0.0, radius * sin(angle))
            else -> Triple(0.0, 0.0, 0.0)
          }
          val loc = center.clone().add(x, y, z)
          ParticleBuilder(Particle.SOUL).extra(0.0).location(loc).receivers(*players).spawn()
        }
      }

      fun drawDynamicRing(offset: Double, axisVector: Vector) {
        val axis = axisVector.normalize()
        val arbitrary = if (abs(axis.dot(Vector(0.0, 1.0, 0.0))) > 0.99) Vector(1.0, 0.0, 0.0)
        else Vector(0.0, 1.0, 0.0)
        val u = axis.clone().crossProduct(arbitrary).normalize()
        val v = axis.clone().crossProduct(u).normalize()

        for (i in 0 until particleCount) {
          val angle = (time * 0.1 + i * (2 * Math.PI / particleCount) + offset) % (2 * Math.PI)
          val pos = u.clone().multiply(cos(angle)).add(v.clone().multiply(sin(angle))).multiply(radius)
          val loc = center.clone().add(pos)
          ParticleBuilder(Particle.SOUL).extra(0.0).location(loc).receivers(*players).spawn()
        }
      }

      // 地面に水平なリング
      drawRing(0.0, "horizontal")

      // 視線方向にリングの面が向く（XZ平面で90度回転）
      drawDynamicRing(Math.PI / 5, perpendicular)

      // 斜めリング（視線方向±Yで傾ける）
      val diagonal1 = perpendicular.clone().add(Vector(0.0, 1.0, 0.0)).normalize()
      val diagonal2 = perpendicular.clone().subtract(Vector(0.0, 1.0, 0.0)).normalize()

      drawDynamicRing(Math.PI / 2, diagonal1)
      drawDynamicRing(Math.PI / 4, diagonal2)
    }
  }

  override fun filter(): Collection<LivingEntity> {
    return center.getNearbyLivingEntities(sqrt(radiusSquared))
  }
}
