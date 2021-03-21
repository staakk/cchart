package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.data.Viewport

data class RendererContext(
    val canvasSize: Size,
    val scaleX: Float,
    val scaleY: Float,
    val bounds: Viewport,
) {
    fun dataToRendererCoordX(x: Float) = (x - bounds.minX) * scaleX

    fun dataToRendererCoordY(y: Float) = -(y - bounds.minY) * scaleY

    fun dataToRendererSizeX(x: Float) = x * scaleX

    fun dataToRendererSizeY(y: Float) = y * scaleY

    fun rendererToDataCoordX(x: Float) = x / scaleX + bounds.minX

    fun rendererToDataCoordY(y: Float) = -y / scaleY + bounds.minY
}

fun rendererContext(
    bounds: Viewport,
    canvasSize: Size,
) = RendererContext(
    canvasSize = canvasSize,
    scaleX = canvasSize.width / bounds.width,
    scaleY = canvasSize.height / bounds.height,
    bounds = bounds
)