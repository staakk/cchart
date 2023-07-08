package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.data.Viewport

private fun getDrawingBounds(chartContext: ChartContext, size: Size): Viewport {
    val bounds = chartContext.viewport
    val xScaledRadius = size.width / 2 / chartContext.scaleX
    val yScaledRadius = size.height / 2 / chartContext.scaleY
    return Viewport(
        minX = bounds.minX - xScaledRadius,
        maxX = bounds.maxX + xScaledRadius,
        minY = bounds.minY - yScaledRadius,
        maxY = bounds.maxY + yScaledRadius
    )
}