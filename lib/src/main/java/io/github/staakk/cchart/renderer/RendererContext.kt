package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.data.Viewport

data class RendererContext(
    val canvasSize: Size,
    val scaleX: Float,
    val scaleY: Float,
    val viewport: Viewport,
) {
    fun dataToRendererCoordX(x: Float) = (x - viewport.minX) * scaleX

    fun dataToRendererCoordY(y: Float) = canvasSize.height - (y - viewport.minY) * scaleY

    fun dataToRendererSizeX(x: Float) = x * scaleX

    fun dataToRendererSizeY(y: Float) = y * scaleY

    fun rendererToDataCoordX(x: Float) = x / scaleX + viewport.minX

    fun rendererToDataCoordY(y: Float) = (canvasSize.height - y) / scaleY - viewport.minY
}

fun rendererContext(
    viewport: Viewport,
    canvasSize: Size,
) = RendererContext(
    canvasSize = canvasSize,
    scaleX = canvasSize.width / viewport.width,
    scaleY = canvasSize.height / viewport.height,
    viewport = viewport
)