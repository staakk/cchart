package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport

private class PointRenderer(
    private val brush: Brush,
    private val radius: Float,
    private val alpha: Float,
    private val style: DrawStyle,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
) : SeriesRenderer {

    override fun DrawScope.render(
        context: RendererContext,
        series: Series
    ): List<RenderedShape> = series.getPointsInViewport(getDrawingBounds(context))
        .map { point ->
            val x = context.dataToRendererCoordX(point.x)
            val y = context.dataToRendererCoordY(point.y)
            drawCircle(
                brush = brush,
                radius = radius,
                center = Offset(x, y),
                alpha = alpha,
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode,
            )
            RenderedShape.Circle(
                point = point,
                seriesName = series.name,
                labelAnchorX = x,
                labelAnchorY = y,
                center = Offset(x = x, y = y),
                radius = radius
            )
        }

    private fun getDrawingBounds(rendererContext: RendererContext): Viewport {
        val bounds = rendererContext.bounds
        val xScaledRadius = radius / rendererContext.scaleX
        val yScaledRadius = radius / rendererContext.scaleY
        return Viewport(
            minX = bounds.minX - xScaledRadius,
            maxX = bounds.maxX + xScaledRadius,
            minY = bounds.minY - yScaledRadius,
            maxY = bounds.maxY + yScaledRadius
        )
    }
}

fun pointRenderer(
    brush: Brush = SolidColor(Color.Black),
    radius: Float = 15f,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): SeriesRenderer = PointRenderer(brush, radius, alpha, style, colorFilter, blendMode)