package io.github.staakk.cchart.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.renderer.ChartContext

private class SimpleGridRenderer(
    private val orientation: GridOrientation,
    private val gridLinesProvider: GridLinesProvider,
    private val lineStyle: LineStyle,
) : GridRenderer {

    override fun DrawScope.render(context: ChartContext) {
        gridLinesProvider
            .provide(
                orientation.getMin(context.viewport),
                orientation.getMax(context.viewport)
            )
            .forEach {
                with(lineStyle) {
                    drawLine(
                        start = orientation.getStart(this@render, context, it),
                        end = orientation.getEnd(this@render, context, it),
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

/**
 * Orientation of the grid.
 */
enum class GridOrientation {
    HORIZONTAL {
        override fun getMin(bounds: Viewport) = bounds.minY

        override fun getMax(bounds: Viewport) = bounds.maxY

        override fun getStart(drawScope: DrawScope, context: ChartContext, value: Float) =
            Offset(0f, context.toRendererY(value))

        override fun getEnd(drawScope: DrawScope, context: ChartContext, value: Float) =
            Offset(drawScope.size.width, context.toRendererY(value))
    },
    VERTICAL {
        override fun getMin(bounds: Viewport) = bounds.minX

        override fun getMax(bounds: Viewport) = bounds.maxX

        override fun getStart(drawScope: DrawScope, context: ChartContext, value: Float) =
            Offset(context.toRendererX(value), 0f)

        override fun getEnd(drawScope: DrawScope, context: ChartContext, value: Float) =
            Offset(context.toRendererX(value), drawScope.size.height)
    };

    abstract fun getMin(bounds: Viewport): Float

    abstract fun getMax(bounds: Viewport): Float

    abstract fun getStart(drawScope: DrawScope, context: ChartContext, value: Float): Offset

    abstract fun getEnd(drawScope: DrawScope, context: ChartContext, value: Float): Offset
}

/**
 * Creates renderer for the grid.
 */
fun gridRenderer(
    orientation: GridOrientation = GridOrientation.HORIZONTAL,
    gridLinesProvider: GridLinesProvider = GridLinesProviders.intGrid,
    lineStyle: LineStyle = LineStyle(),
): GridRenderer = SimpleGridRenderer(
    orientation,
    gridLinesProvider,
    lineStyle,
)
