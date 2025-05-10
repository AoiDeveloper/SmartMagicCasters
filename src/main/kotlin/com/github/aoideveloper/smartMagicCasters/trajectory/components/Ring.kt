package com.github.aoideveloper.smartMagicCasters.trajectory.components

import com.github.aoideveloper.smartMagicCasters.trajectory.BaseTrajectoryComponent
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import org.joml.Vector3f

class Ring : BaseTrajectoryComponent() {
  var radius: Double = 1.0
  var axis: Vector3f = Vector3f(0f, 1f, 0f)
  var count: Int = 10
  var offset: Double = 0.0

  override fun generatePoints(): List<Vector3f> {
    points.clear()
    val angleStep = 2.0 * PI / count

    // 基準となるリングの平面を生成（XZ平面）
    for (i in 0 until count) {
      val angle = angleStep * i + offset
      val x = radius * cos(angle)
      val z = radius * sin(angle)
      val point = Vector3f(x.toFloat(), 0f, z.toFloat())

      // 軸に合わせて回転
      // デフォルトのY軸（0,1,0）から指定された軸への回転を計算
      val defaultAxis = Vector3f(0f, 1f, 0f)
      val rotationAxis = Vector3f(defaultAxis).cross(axis).normalize()
      val rotationAngle = defaultAxis.angle(axis)

      val rotatedPoint =
        if (rotationAxis.length() > 0.001f) {
          rotatePoint(point, rotationAxis, rotationAngle)
        } else {
          point
        }

      points.add(rotatedPoint)
    }

    return points
  }

  override fun preview(): String {
    return "Ring:\n" +
      "Radius: $radius\n" +
      "Axis: $axis\n" +
      "Points: $count\n" +
      "Offset: $offset\n" +
      super.preview()
  }
}
