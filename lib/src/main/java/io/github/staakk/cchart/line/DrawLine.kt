package io.github.staakk.cchart.line

import androidx.compose.ui.graphics.drawscope.clipRect
import io.github.staakk.cchart.renderer.PointsRenderer
import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.LineStyle

class DrawLine(
    private val style: LineStyle = LineStyle(),
) : PointsRenderer {

    override fun RendererScope.draw(rendererPoints: List<RendererPoint<*>>) = clipRect {
        rendererPoints
            .windowed(2)
            .forEach { (start, end) ->
                with(style) {
                    drawLine(
                        start = start.toOffset(),
                        end = end.toOffset(),
                        alpha = alpha,
                        brush = brush,
                        colorFilter = colorFilter,
                        blendMode = blendMode,
                        strokeWidth = strokeWidth,
                        cap = cap,
                        pathEffect = pathEffect,
                    )
                }
            }
    }

}