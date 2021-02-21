package io.github.staakk.cchart.grid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.renderer.RendererContext

class SimpleGridRenderer(
    private val brush: Brush,
    private val orientation: Orientation,
    private val strokeWidth: Float,
    private val cap: StrokeCap,
    private val pathEffect: PathEffect?,
    private val alpha: Float,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
    private val gridLinesProvider: GridLinesProvider,
) : GridRenderer {

    enum class Orientation {
        HORIZONTAL {
            override fun getMin(bounds: DataBounds) = bounds.minY

            override fun getMax(bounds: DataBounds) = bounds.maxY

            override fun getStart(drawScope: DrawScope, context: RendererContext, value: Float) =
                Offset(0f, context.dataToRendererCoordY(value))

            override fun getEnd(drawScope: DrawScope, context: RendererContext, value: Float) =
                Offset(drawScope.size.width, context.dataToRendererCoordY(value))
        },
        VERTICAL {
            override fun getMin(bounds: DataBounds) = bounds.minX

            override fun getMax(bounds: DataBounds) = bounds.maxX

            override fun getStart(drawScope: DrawScope, context: RendererContext, value: Float) =
                Offset(context.dataToRendererCoordX(value), 0f)

            override fun getEnd(drawScope: DrawScope, context: RendererContext, value: Float) =
                Offset(context.dataToRendererCoordX(value), -drawScope.size.height)
        };

        abstract fun getMin(bounds: DataBounds): Float

        abstract fun getMax(bounds: DataBounds): Float

        abstract fun getStart(drawScope: DrawScope, context: RendererContext, value: Float): Offset

        abstract fun getEnd(drawScope: DrawScope, context: RendererContext, value: Float): Offset
    }

    override fun DrawScope.render(context: RendererContext) {
        gridLinesProvider.provide(
            orientation.getMin(context.bounds),
            orientation.getMax(context.bounds)
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

fun gridRenderer(
    brush: Brush = SolidColor(Color.Black),
    orientation: SimpleGridRenderer.Orientation = SimpleGridRenderer.Orientation.HORIZONTAL,
    gridLinesProvider: GridLinesProvider = IntGridLinesProvider,
    strokeWidth: Float = Stroke.HairlineWidth,
    cap: StrokeCap = Stroke.DefaultCap,
    pathEffect: PathEffect? = null,
    alpha: Float = 0.2f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) = SimpleGridRenderer(
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