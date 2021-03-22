package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Viewport

typealias CircleDrawer = DrawScope.(Point, center: Offset, radius: Float) -> Unit
typealias CircleBoundingShapeProvider = DrawScope.(Point, center: Offset, radius: Float) -> BoundingShape

fun pointRenderer(
    radius: Float = 15f,
    circleDrawer: CircleDrawer = drawCircle(),
    boundingShapeProvider: CircleBoundingShapeProvider = circleBoundingShapeProvider()
) =
    SeriesRenderer { context, series ->
        series.getPointsInViewport(getDrawingBounds(context, radius))
            .map { point ->
                val x = context.dataToRendererCoordX(point.x)
                val y = context.dataToRendererCoordY(point.y)
                circleDrawer(this, point, Offset(x, y), radius)
                boundingShapeProvider(this, point, Offset(x, y), radius)
            }
    }

fun drawCircle(
    brush: Brush = SolidColor(Color.Black),
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): CircleDrawer = { _, center, radius ->
    drawCircle(
        brush = brush,
        radius = radius,
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
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


fun circleBoundingShapeProvider(): CircleBoundingShapeProvider = { point, center, radius ->
    BoundingShape.Circle(
        point = point,
        labelAnchorX = center.x,
        labelAnchorY = center.y,
        center = center,
        radius = radius
    )
}