package io.github.staakk.cchart.renderer.axis

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.renderer.Renderer
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.LineStyle

class Axis(
    private val orientation: AxisOrientation,
    private val positionPercent: Float,
    private val style: LineStyle = LineStyle(),
) : Renderer {

    override fun RendererScope.draw() {
        val x = positionPercent * size.width
        val y = (1f - positionPercent) * size.height
        val start = Offset(x * orientation.y, y * orientation.x)
        val end = Offset(
            size.width * orientation.x + x * orientation.y,
            size.height * orientation.y + y * orientation.x,
        )

        with(style) {
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
}