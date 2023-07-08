package io.github.staakk.cchart.renderer.line

import io.github.staakk.cchart.renderer.Drawer
import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.LineStyle

class DrawLine(
    private val style: LineStyle = LineStyle(),
) : Drawer {

    override fun RendererScope.draw(index: Int, rendererPoints: List<RendererPoint<*>>) {
        if (index == rendererPoints.size - 1) return
        val start = rendererPoints[index]
        val end = rendererPoints[index + 1]
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