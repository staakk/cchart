package io.github.staakk.composechart.util

import kotlin.math.cos
import kotlin.math.sin

internal data class Vec2(
    val x: Double,
    val y: Double,
) {
    operator fun times(value: Double) = Vec2(x * value, y * value)
}

internal fun angleToVec(angleDeg: Double): Vec2 {
    val angleRad = angleDeg.toRadian()
    return Vec2(cos(angleRad), sin(angleRad))
}

internal fun Double.toRadian(): Double = this / 180 * Math.PI
