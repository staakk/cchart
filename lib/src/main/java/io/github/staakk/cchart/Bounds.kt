package io.github.staakk.cchart

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs


data class Bounds(
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float
) {
    operator fun plus(offset: Offset) = Bounds(
        minX = minX + offset.x,
        maxX = maxX + offset.x,
        minY = minY + offset.y,
        maxY = maxY + offset.y
    )

    fun withZoom(zoom: Float): Bounds {
        val dx = abs(maxX - minX)
        val dy = abs(maxY - minY)
        val zdx = dx / zoom
        val zdy = dy / zoom
        val ddx = (dx - zdx) / 2
        val ddy = (dy - zdy) / 2
        return Bounds(
            minX = minX + ddx,
            maxX = maxX - ddx,
            minY = minY + ddy,
            maxY = maxY - ddy
        )
    }
}