package io.github.staakk.composechart.renderer

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Density
import io.github.staakk.composechart.Bounds
import kotlin.math.abs

data class RendererContext(
    val canvasSize: Size,
    val density: Density,
    val scaleX: Float,
    val scaleY: Float,
    val bounds: Bounds,
) {
    fun dataToRendererCoordX(x: Float) = (x - bounds.minX) * scaleX

    fun dataToRendererCoordY(y: Float) = -(y - bounds.minY) * scaleY

    fun dataToRendererSizeX(x: Float) = x * scaleX

    fun dataToRendererSizeY(y: Float) = -y * scaleY
}

fun rendererContext(
    bounds: Bounds,
    density: Density,
    canvasSize: Size,
) = RendererContext(
    canvasSize = canvasSize,
    density = density,
    scaleX = canvasSize.width / abs(bounds.maxX - bounds.minX),
    scaleY = canvasSize.height / abs(bounds.maxY - bounds.minY),
    bounds = bounds
)