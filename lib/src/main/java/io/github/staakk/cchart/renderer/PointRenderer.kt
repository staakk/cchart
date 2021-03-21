package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Viewport

fun pointRenderer(
    radius: Float = 15f,
    render: DrawScope.(Point, center: Offset, radius: Float) -> RenderedShape
): SeriesRenderer = SeriesRenderer { context, series ->
    series.getPointsInViewport(getDrawingBounds(context, radius))
        .map { point ->
            val x = context.dataToRendererCoordX(point.x)
            val y = context.dataToRendererCoordY(point.y)
            render(this, point, Offset(x, y), radius)
        }
}

fun renderCircle(
    brush: Brush = SolidColor(Color.Black),
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): DrawScope.(Point, Offset, Float) -> RenderedShape = { point, center, radius ->
    drawCircle(
        brush = brush,
        radius = radius,
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
    RenderedShape.Circle(
        point = point,
        seriesName = "series.name",
        labelAnchorX = center.x,
        labelAnchorY = center.y,
        center = center,
        radius = radius
    )
}

private fun getDrawingBounds(rendererContext: RendererContext, radius: Float): Viewport {
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