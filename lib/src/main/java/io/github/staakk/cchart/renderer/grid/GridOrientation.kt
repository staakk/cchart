package io.github.staakk.cchart.renderer.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.renderer.ChartContext

/**
 * Orientation of the grid.
 */
enum class GridOrientation {
    Horizontal {
        override fun getMin(bounds: Viewport) = bounds.minY

        override fun getMax(bounds: Viewport) = bounds.maxY

        override fun getStart(drawScope: DrawScope, context: ChartContext, value: Float) =
            Offset(0f, context.toRendererY(value))

        override fun getEnd(drawScope: DrawScope, context: ChartContext, value: Float) =
            Offset(drawScope.size.width, context.toRendererY(value))
    },
    Vertical {
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
