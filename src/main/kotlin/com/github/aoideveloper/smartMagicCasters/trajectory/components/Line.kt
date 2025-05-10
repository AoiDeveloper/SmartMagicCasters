package com.github.aoideveloper.smartMagicCasters.trajectory.components

import com.github.aoideveloper.smartMagicCasters.trajectory.BaseTrajectoryComponent
import org.joml.Vector3f

class Line : BaseTrajectoryComponent() {
    var start: Vector3f = Vector3f(0f, 0f, 0f)
    var end: Vector3f = Vector3f(0f, 0f, 0f)
    var count: Int = 10

    override fun generatePoints(): List<Vector3f> {
        points.clear()
        val direction = Vector3f(end).sub(start)
        val step = 1f / (count - 1)

        for (i in 0 until count) {
            val t = i * step
            val point = Vector3f(start).add(Vector3f(direction).mul(t))
            points.add(point)
        }

        return points
    }

    override fun preview(): String {
        return "Line:\n" +
            "Start: $start\n" +
            "End: $end\n" +
            "Points: $count\n" +
            super.preview()
    }
} 