package io.github.staakk.cchart

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.data.Viewport

data class ChartContext(
    val canvasSize: Size,
    val scaleX: Float,
    val scaleY: Float,
    val viewport: Viewport,
) {
    fun toRendererX(x: Float) = (x - viewport.minX) * scaleX

    fun toRendererY(y: Float) = canvasSize.height - (y - viewport.minY) * scaleY

    fun toChartX(x: Float) = x / scaleX + viewport.minX

    fun toChartY(y: Float) = (canvasSize.height - y) / scaleY - viewport.minY

    fun toRendererWidth(width: Float) = width * scaleX

    fun toRendererHeight(height: Float) = height * scaleY

    fun toChartWidth(width: Float) = width / scaleX

    fun toChartHeight(height: Float) = height / scaleY

    fun getDrawingBounds(chartContext: ChartContext, size: Size): Viewport {
        val scaledWidth = size.width / 2 / chartContext.scaleX
        val scaledHeight = size.height / 2 / chartContext.scaleY
        return Viewport(
            minX = viewport.minX - scaledWidth,
            maxX = viewport.maxX + scaledWidth,
            minY = viewport.minY - scaledHeight,
            maxY = viewport.maxY + scaledHeight
        )
    }
}

fun chartContext(
    viewport: Viewport,
    canvasSize: Size,
) = ChartContext(
    canvasSize = canvasSize,
    scaleX = canvasSize.width / viewport.width,
    scaleY = canvasSize.height / viewport.height,
    viewport = viewport
)