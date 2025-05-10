package com.github.aoideveloper.smartMagicCasters.trajectory.components

import com.github.aoideveloper.smartMagicCasters.trajectory.BaseTrajectoryComponent
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import org.joml.Vector3f

class Spiral : BaseTrajectoryComponent() {
  var radius: Double = 1.0
  var height: Double = 3.0
  var turns: Int = 2
  var count: Int = 50
  var axis: Vector3f = Vector3f(0f, 1f, 0f)

  override fun generatePoints(): List<Vector3f> {
    points.clear()
    val angleStep = 2.0 * PI * turns / count
    val heightStep = height / count

    for (i in 0 until count) {
      val angle = angleStep * i
      val currentHeight = heightStep * i
      val x = radius * cos(angle)
      val z = radius * sin(angle)
      val point = Vector3f(x.toFloat(), currentHeight.toFloat(), z.toFloat())

      // 軸に合わせて回転
      val rotatedPoint = rotatePoint(point, axis, 0f)
      points.add(rotatedPoint)
    }

    return points
  }

  override fun preview(): String {
    return "Spiral:\n" +
      "Radius: $radius\n" +
      "Height: $height\n" +
      "Turns: $turns\n" +
      "Points: $count\n" +
      "Axis: $axis\n" +
      super.preview()
  }
}
