@file:Suppress("unused")

package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.renderer.ChartContext

/**
 * Interface for axis renderers.
 */
fun interface AxisRenderer {
    /**
     * Function called when axis needs to be drawn.
     */
    fun DrawScope.render(context: ChartContext)
}

fun axisRenderer(
    orientation: Orientation,
    positionPercent: Float,
    axisDrawer: AxisDrawer = axisDrawer(LineStyle())
) = AxisRenderer {
    val xPos = positionPercent * size.width
    val yPos = (1f - positionPercent) * size.height
    with(axisDrawer) {
        draw(
            start = Offset(xPos * orientation.y, yPos * orientation.x),
            end = Offset(
                size.width * orientation.x + xPos * orientation.y,
                size.height * orientation.y + yPos * orientation.x,
            )
        )
    }
}

data class Orientation(val x: Float, val y: Float) {
    companion object {
        val Horizontal = Orientation(1f, 0f)
        val Vertical = Orientation(0f, 1f)
    }
}