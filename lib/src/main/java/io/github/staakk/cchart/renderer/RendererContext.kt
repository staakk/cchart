package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.data.DataBounds
import kotlin.math.abs

data class RendererContext(
    val canvasSize: Size,
    val scaleX: Float,
    val scaleY: Float,
    val bounds: DataBounds,
) {
    fun dataToRendererCoordX(x: Float) = (x - bounds.minX) * scaleX

    fun dataToRendererCoordY(y: Float) = -(y - bounds.minY) * scaleY

    fun dataToRendererSizeX(x: Float) = x * scaleX

    fun dataToRendererSizeY(y: Float) = y * scaleY
}

fun rendererContext(
    bounds: DataBounds,
    canvasSize: Size,
) = RendererContext(
    canvasSize = canvasSize,
    scaleX = canvasSize.width / abs(bounds.maxX - bounds.minX),
    scaleY = canvasSize.height / abs(bounds.maxY - bounds.minY),
    bounds = bounds
)