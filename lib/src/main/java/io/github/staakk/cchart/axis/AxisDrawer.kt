package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.style.LineStyle

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