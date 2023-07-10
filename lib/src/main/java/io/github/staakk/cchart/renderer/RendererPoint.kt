package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.ChartContext
import io.github.staakk.cchart.data.Data

data class RendererPoint<out T: Data<T>>(
    val data: Data<T>,
    val x: Float,
    val y: Float,
) {

    fun toOffset() = Offset(x, y)
}

fun <T : Data<T>> Data<T>.toRendererPoint(chartContext: ChartContext) = RendererPoint(
    data = this,
    x = chartContext.toRendererX(x),
    y = chartContext.toRendererY(y),
)