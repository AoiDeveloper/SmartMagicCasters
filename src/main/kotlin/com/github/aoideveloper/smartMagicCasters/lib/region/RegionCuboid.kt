package com.github.aoideveloper.smartMagicCasters.lib.region

import com.github.aoideveloper.smartMagicCasters.lib.region.Region3D
import com.github.aoideveloper.smartMagicCasters.lib.util.log.I18n
import com.github.aoideveloper.smartMagicCasters.lib.util.normalizeCorners
import com.github.aoideveloper.smartMagicCasters.trajectory.trajectory
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.joml.Vector3f
import kotlin.math.abs

class RegionCuboid private constructor(val min: Location, val max: Location) : Region3D {

  init {
    require(min.world == max.world) {
      I18n.msg("log.location.different_world", min.world.name, max.world.name)
    }
  }

  companion object {
    fun fromCorners(loc1: Location, loc2: Location): RegionCuboid {
      require(loc1.world == loc2.world) {
        I18n.msg("log.location.different_world", loc1.world.name, loc2.world.name)
      }

      val (min, max) = normalizeCorners(loc1, loc2)
      return RegionCuboid(min, max)
    }

    fun fromCenterAndSize(center: Location, width: Int, height: Int, depth: Int): RegionCuboid {
      val halfW = width / 2.0
      val halfH = height / 2.0
      val halfD = depth / 2.0

      val min = center.clone().add(-halfW, -halfH, -halfD)
      val max = center.clone().add(halfW, halfH, halfD)

      return fromCorners(min, max)
    }

    fun fromStartAndSize(start: Location, width: Int, height: Int, depth: Int): RegionCuboid {
      val end = start.clone().add(width.toDouble(), height.toDouble(), depth.toDouble())
      return fromCorners(start, end)
    }
  }

  override fun contains(location: Location): Boolean {
    if (location.world != min.world) return false
    return location.let { loc ->
      loc.x in min.x..max.x && loc.y in min.y..max.y && loc.z in min.z..max.z
    }
  }

  override fun visualize(): (Array<Player>) -> Unit {
    return { players ->
      val time = System.currentTimeMillis() / 1000.0
      val width = max.x - min.x
      val height = max.y - min.y
      val depth = max.z - min.z

      val trajectory = trajectory {
        particle {
          type = Particle.SOUL
          count = 1
          offset = Triple(0.1, 0.1, 0.1)
          speed = 0.0
        }

        // 底面の4辺
        line {
          start = Vector3f((-width/2).toFloat(), (-height/2).toFloat(), (-depth/2).toFloat())
          end = Vector3f((width/2).toFloat(), (-height/2).toFloat(), (-depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((width/2).toFloat(), (-height/2).toFloat(), (-depth/2).toFloat())
          end = Vector3f((width/2).toFloat(), (-height/2).toFloat(), (depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((width/2).toFloat(), (-height/2).toFloat(), (depth/2).toFloat())
          end = Vector3f((-width/2).toFloat(), (-height/2).toFloat(), (depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((-width/2).toFloat(), (-height/2).toFloat(), (depth/2).toFloat())
          end = Vector3f((-width/2).toFloat(), (-height/2).toFloat(), (-depth/2).toFloat())
          count = 10
        }

        // 上面の4辺
        line {
          start = Vector3f((-width/2).toFloat(), (height/2).toFloat(), (-depth/2).toFloat())
          end = Vector3f((width/2).toFloat(), (height/2).toFloat(), (-depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((width/2).toFloat(), (height/2).toFloat(), (-depth/2).toFloat())
          end = Vector3f((width/2).toFloat(), (height/2).toFloat(), (depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((width/2).toFloat(), (height/2).toFloat(), (depth/2).toFloat())
          end = Vector3f((-width/2).toFloat(), (height/2).toFloat(), (depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((-width/2).toFloat(), (height/2).toFloat(), (depth/2).toFloat())
          end = Vector3f((-width/2).toFloat(), (height/2).toFloat(), (-depth/2).toFloat())
          count = 10
        }

        // 垂直の4辺
        line {
          start = Vector3f((-width/2).toFloat(), (-height/2).toFloat(), (-depth/2).toFloat())
          end = Vector3f((-width/2).toFloat(), (height/2).toFloat(), (-depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((width/2).toFloat(), (-height/2).toFloat(), (-depth/2).toFloat())
          end = Vector3f((width/2).toFloat(), (height/2).toFloat(), (-depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((width/2).toFloat(), (-height/2).toFloat(), (depth/2).toFloat())
          end = Vector3f((width/2).toFloat(), (height/2).toFloat(), (depth/2).toFloat())
          count = 10
        }
        line {
          start = Vector3f((-width/2).toFloat(), (-height/2).toFloat(), (depth/2).toFloat())
          end = Vector3f((-width/2).toFloat(), (height/2).toFloat(), (depth/2).toFloat())
          count = 10
        }
      }

      // 中心位置を計算
      val center = Location(
        min.world,
        min.x + width / 2,
        min.y + height / 2,
        min.z + depth / 2
      )

      trajectory.spawnParticles(center, center.world!!, players)
    }
  }

  override fun filter(): Collection<LivingEntity> {
    return min.world!!.getNearbyLivingEntities(
      min.clone().add(max).multiply(0.5),
      abs(max.x - min.x) / 2,
      abs(max.y - min.y) / 2,
      abs(max.z - min.z) / 2
    )
  }
}
