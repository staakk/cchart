package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Viewport

fun interface PointDrawer {

    /**
     * Draws shape based on the provided values.
     *
     * @param data Point to draw.
     * @param center Center of the shape to draw.
     * @param size Size of the shape to draw.
     */
    fun DrawScope.draw(data: Data, center: Offset, size: Size)
}

fun interface PointBoundingShapeProvider {

    /**
     * Provides bounding box for the shape.
     *
     * @param data Point to draw
     * @param center Center of the rendered shape.
     * @param size Radius of the rendered shape.
     */
    fun DrawScope.provide(data: Data, center: Offset, size: Size): BoundingShape
}

/**
 * Creates [SeriesRenderer] that renders points.
 *
 * @param size Size of the points to render in pixels.
 * @param pointDrawer A function drawing the point.
 * @param boundingShapeProvider Provider of the [BoundingShape].
 *
 * @see [io.github.staakk.cchart.ChartScope.series]
 */
fun pointRenderer(
    size: Size = Size(30f, 30f),
    pointDrawer: PointDrawer = drawCircle(),
    boundingShapeProvider: PointBoundingShapeProvider = circleBoundingShapeProvider()
) = SeriesRenderer { context, series ->
    series.getPointsInViewport(getDrawingBounds(context, size))
        .map { point ->
            val x = context.dataToRendererCoordX(point.x)
            val y = context.dataToRendererCoordY(point.y)
            with(pointDrawer) { draw(point, Offset(x, y), size) }
            with(boundingShapeProvider) { provide(point, Offset(x, y), size) }
        }
}

fun drawCircle(
    brush: Brush = SolidColor(Color.Black),
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) = PointDrawer { _, center, size ->
    drawCircle(
        brush = brush,
        radius = size.height / 2,
        center = center,
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
}

private fun getDrawingBounds(rendererContext: RendererContext, size: Size): Viewport {
    val bounds = rendererContext.bounds
    val xScaledRadius = size.width / 2 / rendererContext.scaleX
    val yScaledRadius = size.height / 2 / rendererContext.scaleY
    return Viewport(
        minX = bounds.minX - xScaledRadius,
        maxX = bounds.maxX + xScaledRadius,
        minY = bounds.minY - yScaledRadius,
        maxY = bounds.maxY + yScaledRadius
    )
}

fun circleBoundingShapeProvider() = PointBoundingShapeProvider { point, center, size ->
    BoundingShape.Circle(
        data = point,
        labelAnchorX = center.x,
        labelAnchorY = center.y,
        center = center,
        radius = size.width / 2
    )
}