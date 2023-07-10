package io.github.staakk.cchart.grid

import io.github.staakk.cchart.renderer.Renderer
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.LineStyle

class Grid(
    private val orientation: GridOrientation,
    private val gridLinesProvider: GridProvider = GridLinesProviders.intGrid,
    private val lineStyle: LineStyle = LineStyle(),
) : Renderer {

    override fun RendererScope.draw() {
        gridLinesProvider
            .provide(
                orientation.getMin(chartContext.viewport),
                orientation.getMax(chartContext.viewport)
            )
            .forEach {
                with(lineStyle) {
                    drawLine(
                        start = orientation.getStart(this@draw, chartContext, it),
                        end = orientation.getEnd(this@draw, chartContext, it),
                        cap = cap,
                        pathEffect = pathEffect,
                        alpha = alpha,
                        brush = brush,
                        colorFilter = colorFilter,
                        strokeWidth = strokeWidth,
                        blendMode = blendMode
                    )
                }
            }
    }
}