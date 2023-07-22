package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.ChartContext
import io.github.staakk.cchart.data.Point

data class RendererPoint<out T>(
    val point: Point<T>,
    val x: Float,
    val y: Float,
) {
    private val offset by lazy { Offset(x, y) }

    fun toOffset() = offset
}

fun <T> Point<T>.toRendererPoint(chartContext: ChartContext) = RendererPoint(
    point = this,
    x = chartContext.toRendererX(x),
    y = chartContext.toRendererY(y),
)