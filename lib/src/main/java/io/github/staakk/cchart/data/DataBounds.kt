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
}