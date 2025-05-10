package com.github.aoideveloper.smartMagicCasters.trajectory

import org.bukkit.Particle

class ParticleSettings {
  var type: Particle = Particle.FLAME
  var count: Int = 1
  var offset: Triple<Double, Double, Double> = Triple(0.0, 0.0, 0.0)
  var speed: Double = 0.0
  var data: Any? = null
}
