package io.github.staakk.cchart.renderer.line

import androidx.compose.ui.graphics.drawscope.clipRect
import io.github.staakk.cchart.renderer.Drawer
import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.LineStyle

class DrawLine(
    private val style: LineStyle = LineStyle(),
) : Drawer {

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