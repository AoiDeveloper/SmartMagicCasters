package com.github.aoideveloper.smartMagicCasters.trajectory

import org.joml.Quaternionf
import org.joml.Vector3f

interface TrajectoryComponent {
  fun generatePoints(): List<Vector3f>

  fun preview(): String
}

abstract class BaseTrajectoryComponent : TrajectoryComponent {
  protected val points = mutableListOf<Vector3f>()

  protected fun rotatePoint(point: Vector3f, axis: Vector3f, angle: Float): Vector3f {
    val quaternion = Quaternionf().fromAxisAngleRad(axis, angle)
    return Vector3f(point).rotate(quaternion)
  }

  override fun preview(): String {
    return "Points: ${points.size}\n" +
      "First point: ${points.firstOrNull()}\n" +
      "Last point: ${points.lastOrNull()}"
  }
}
