package io.github.staakk.cchart.data

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs


data class DataBounds(
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float
) {

    val width = abs(maxX - minX)
    val height = abs(maxY - minY)

    operator fun plus(offset: Offset) = DataBounds(
        minX = minX + offset.x,
        maxX = maxX + offset.x,
        minY = minY + offset.y,
        maxY = maxY + offset.y
    )

    fun withZoom(zoom: Float): DataBounds {
        val dx = abs(maxX - minX)
        val dy = abs(maxY - minY)
        val zdx = dx / zoom
        val zdy = dy / zoom
        val ddx = (dx - zdx) / 2
        val ddy = (dy - zdy) / 2
        return DataBounds(
            minX = minX + ddx,
            maxX = maxX - ddx,
            minY = minY + ddy,
            maxY = maxY - ddy
        )
    }

    companion object {
        fun Iterable<Series>.getBounds(): DataBounds {
            var maxX = Float.MIN_VALUE
            var minX = Float.MAX_VALUE
            var maxY = Float.MIN_VALUE
            var minY = Float.MAX_VALUE

            forEach { s ->
                s.points.forEach { p ->
                    if (p.x > maxX) maxX = p.x
                    if (p.x < minX) minX = p.x
                    if (p.y > maxY) maxY = p.y
                    if (p.y < minY) minY = p.y
                }
            }

            return DataBounds(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }
    }
}