import com.github.aoideveloper.smartMagicCasters.lib.region.Region3D
import com.github.aoideveloper.smartMagicCasters.lib.util.log.I18n
import com.github.aoideveloper.smartMagicCasters.lib.util.normalizeCorners
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

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
    TODO("Not yet implemented")
  }

  override fun filter(): Collection<LivingEntity> {
    TODO()
  }
}
