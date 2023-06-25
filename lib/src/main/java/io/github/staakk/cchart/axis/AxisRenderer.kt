@file:Suppress("unused")

package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.dsl.LineStyle
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

fun interface HorizontalAxisRenderer : AxisRenderer {
    companion object {
        const val Top = 0.0f
        const val Bottom = 1.0f
    }
}

fun interface VerticalAxisRenderer : AxisRenderer {
    companion object {
        const val Right = 1f
        const val Left = 0f
    }
}

fun interface AxisDrawer {
    fun DrawScope.draw(start: Offset, end: Offset)
}

fun axisDrawer(builder: LineStyle.() -> Unit) = axisDrawer(LineStyle().apply(builder))

fun axisDrawer(lineStyle: LineStyle) = AxisDrawer { start, end ->
    with(lineStyle) {
        drawLine(
            start = start,
            end = end,
            strokeWidth = strokeWidth,
            alpha = alpha,
            brush = brush,
            pathEffect = pathEffect,
            cap = cap,
            colorFilter = colorFilter,
            blendMode = blendMode
        )
    }
}