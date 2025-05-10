package com.github.aoideveloper.smartMagicCasters.lib.region

import com.github.aoideveloper.smartMagicCasters.SmartMagicCasters
import com.github.aoideveloper.smartMagicCasters.trajectory.trajectory
import kotlin.math.sqrt
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import org.joml.Vector3f

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

      val averageDirection =
        players
          .map { it.location.direction }
          .fold(Vector(0, 0, 0)) { acc, vec -> acc.add(vec) }
          .normalize()

      val lookDir = averageDirection.clone().setY(0.0).normalize()
      val perpendicular = Vector(-lookDir.z, 0.0, lookDir.x).normalize()

      val trajectory = trajectory {
        particle { type = Particle.ELECTRIC_SPARK }
        // 水平リング
        ring {
          this.radius = radius
          axis = Vector3f(0f, 1f, 0f)
          count = 20
          offset = time * 0.1
        }

        // 視線方向に垂直なリング
        ring {
          this.radius = radius
          axis =
            Vector3f(
              perpendicular.x.toFloat(),
              perpendicular.y.toFloat(),
              perpendicular.z.toFloat(),
            )
          count = 20
          offset = time * 0.1 + Math.PI / 5
        }

        // 斜めリング1
        ring {
          this.radius = radius
          val diagonal1 = perpendicular.clone().add(Vector(0.0, 1.0, 0.0)).normalize()
          axis = Vector3f(diagonal1.x.toFloat(), diagonal1.y.toFloat(), diagonal1.z.toFloat())
          count = 20
          offset = time * 0.1 + Math.PI / 2
        }

        // 斜めリング2
        ring {
          this.radius = radius
          val diagonal2 = perpendicular.clone().subtract(Vector(0.0, 1.0, 0.0)).normalize()
          axis = Vector3f(diagonal2.x.toFloat(), diagonal2.y.toFloat(), diagonal2.z.toFloat())
          count = 20
          offset = time * 0.1 + Math.PI / 4
        }
      }

      trajectory.spawnParticles(center, center.world!!, players)
    }
  }

  override fun filter(): Collection<LivingEntity> {
    return center.getNearbyLivingEntities(sqrt(radiusSquared))
  }
}
