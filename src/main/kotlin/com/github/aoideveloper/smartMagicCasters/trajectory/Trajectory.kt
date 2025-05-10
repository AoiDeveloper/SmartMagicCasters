package com.github.aoideveloper.smartMagicCasters.trajectory

import com.github.aoideveloper.smartMagicCasters.trajectory.components.Line
import com.github.aoideveloper.smartMagicCasters.trajectory.components.Ring
import com.github.aoideveloper.smartMagicCasters.trajectory.components.Spiral
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.entity.Player
import org.joml.Vector3f

class Trajectory {
  private val components = mutableListOf<TrajectoryComponent>()
  private var particleType: Particle = Particle.FLAME
  private var particleCount: Int = 1
  private var particleOffset: Triple<Double, Double, Double> = Triple(0.0, 0.0, 0.0)
  private var particleSpeed: Double = 0.0
  private var particleData: Any? = null

  fun ring(init: Ring.() -> Unit) {
    components.add(Ring().apply(init))
  }

  fun spiral(init: Spiral.() -> Unit) {
    components.add(Spiral().apply(init))
  }

  fun line(init: Line.() -> Unit) {
    components.add(Line().apply(init))
  }

  fun particle(init: ParticleSettings.() -> Unit) {
    ParticleSettings().apply(init).let { settings ->
      particleType = settings.type
      particleCount = settings.count
      particleOffset = settings.offset
      particleSpeed = settings.speed
      particleData = settings.data
    }
  }

  fun generatePoints(): List<Vector3f> {
    return components.flatMap { it.generatePoints() }
  }

  fun spawnParticles(center: Location, world: World, players: Array<Player>) {
    val points = generatePoints()

    points.forEach { point ->
      val particleLoc =
        center.clone().add(point.x.toDouble(), point.y.toDouble(), point.z.toDouble())
      world.spawnParticle(
        particleType,
        particleLoc,
        particleCount,
        particleOffset.first,
        particleOffset.second,
        particleOffset.third,
        particleSpeed,
        particleData,
        true,
      )
    }
  }

  fun preview(): String {
    return "Particle Settings:\n" +
      "Type: $particleType\n" +
      "Count: $particleCount\n" +
      "Offset: $particleOffset\n" +
      "Speed: $particleSpeed\n" +
      "Data: $particleData\n\n" +
      components.joinToString("\n\n") { it.preview() }
  }
}

fun trajectory(init: Trajectory.() -> Unit): Trajectory {
  return Trajectory().apply(init)
}
