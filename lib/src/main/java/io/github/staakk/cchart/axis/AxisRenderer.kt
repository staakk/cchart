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

data class AxisOrientation(val x: Float, val y: Float) {
    companion object {
        val Horizontal = AxisOrientation(1f, 0f)
        val Vertical = AxisOrientation(0f, 1f)
    }
}