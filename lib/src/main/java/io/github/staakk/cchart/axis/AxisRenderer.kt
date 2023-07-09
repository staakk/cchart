package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.renderer.RendererScope

/**
 * Interface for axis renderers.
 */
fun interface AxisRenderer {
    /**
     * Function called when axis needs to be drawn.
     */
    fun RendererScope.render()
}

fun axisRenderer(
    axisOrientation: AxisOrientation,
    positionPercent: Float,
    axisDrawer: AxisDrawer = axisDrawer(LineStyle())
) = AxisRenderer {
    val xPos = positionPercent * size.width
    val yPos = (1f - positionPercent) * size.height
    with(axisDrawer) {
        draw(
            start = Offset(xPos * axisOrientation.y, yPos * axisOrientation.x),
            end = Offset(
                size.width * axisOrientation.x + xPos * axisOrientation.y,
                size.height * axisOrientation.y + yPos * axisOrientation.x,
            )
        )
    }
}