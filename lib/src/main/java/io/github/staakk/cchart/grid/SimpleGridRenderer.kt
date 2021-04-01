package io.github.staakk.cchart.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.renderer.ChartContext

private class SimpleGridRenderer(
    private val brush: Brush,
    private val orientation: GridOrientation,
    private val strokeWidth: Float,
    private val cap: StrokeCap,
    private val pathEffect: PathEffect?,
    private val alpha: Float,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
    private val gridLinesProvider: GridLinesProvider,
) : GridRenderer {

    override fun DrawScope.render(context: ChartContext) {
        gridLinesProvider.provide(
            orientation.getMin(context.viewport),
            orientation.getMax(context.viewport)
        ).forEach {
            drawLine(
                brush = brush,
                start = orientation.getStart(this, context, it),
                end = orientation.getEnd(this, context, it),
                strokeWidth = strokeWidth,
                cap = cap,
                pathEffect = pathEffect,
                alpha = alpha,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
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
    brush: Brush = SolidColor(Color.Black),
    orientation: GridOrientation = GridOrientation.HORIZONTAL,
    gridLinesProvider: GridLinesProvider = GridLinesProviders.intGrid,
    strokeWidth: Float = Stroke.HairlineWidth,
    cap: StrokeCap = Stroke.DefaultCap,
    pathEffect: PathEffect? = null,
    alpha: Float = 0.2f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): GridRenderer = SimpleGridRenderer(
    brush,
    orientation,
    strokeWidth,
    cap,
    pathEffect,
    alpha,
    colorFilter,
    blendMode,
    gridLinesProvider
)
