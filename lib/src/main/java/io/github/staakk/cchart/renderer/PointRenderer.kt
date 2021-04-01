package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Viewport

fun interface PointDrawer {

    /**
     * Draws shape based on the provided values.
     *
     * @param chartData Point to draw in the chart space.
     * @param rendererData Point to draw in the renderer space.
     * @param size Size of the shape to draw.
     */
    fun RendererScope.draw(chartData: Data<*>, rendererData: Data<*>, size: Size)
}

fun interface PointBoundingShapeProvider {

    /**
     * Provides bounding box for the shape.
     *
     * @param chartData Point to create bounding box for in the chart space.
     * @param rendererData Point to create bounding box for in the renderer space.
     * @param size Radius of the rendered shape.
     */
    fun RendererScope.provide(chartData: Data<*>, rendererData: Data<*>, size: Size): BoundingShape
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
    pointDrawer: PointDrawer = circleDrawer(),
    boundingShapeProvider: PointBoundingShapeProvider = circleBoundingShapeProvider()
) = SeriesRenderer { series ->
    series.getPointsInViewport(getDrawingBounds(chartContext, size))
        .map { point ->
            val rendererData = point.toRendererData(chartContext)
            with(pointDrawer) { draw(point, rendererData, size) }
            with(boundingShapeProvider) { provide(point, rendererData, size) }
        }
}

fun circleDrawer(
    brush: Brush = SolidColor(Color.Black),
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode
) = PointDrawer { _, rendererData, size ->
    drawCircle(
        brush = brush,
        radius = size.height / 2,
        center = Offset(rendererData.x, rendererData.y),
        alpha = alpha,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode,
    )
}

fun circleWithError(
    brush: Brush = SolidColor(Color.Black),
    alpha: Float = 1.0f,
    strokeWidth: Float = Stroke.HairlineWidth,
    cap: StrokeCap = Stroke.DefaultCap,
    colorFilter: ColorFilter? = null,
    pathEffect: PathEffect? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    circleDrawer: PointDrawer = circleDrawer()
) = PointDrawer { chartData, rendererData, size ->
    if (rendererData is Data.PointWithError) {
        val center = rendererData.toOffset()
        drawLine(
            start = Offset(center.x, center.y + rendererData.errorY),
            end = Offset(center.x, center.y - rendererData.errorY),
            brush = brush,
            alpha = alpha,
            colorFilter = colorFilter,
            blendMode = blendMode,
            strokeWidth = strokeWidth,
            cap = cap,
            pathEffect = pathEffect
        )
        drawLine(
            start = Offset(center.x + rendererData.errorX, center.y),
            end = Offset(center.x - rendererData.errorX, center.y),
            brush = brush,
            alpha = alpha,
            colorFilter = colorFilter,
            blendMode = blendMode,
            strokeWidth = strokeWidth,
            cap = cap,
            pathEffect = pathEffect
        )
    }
    with(circleDrawer) { draw(chartData, rendererData, size) }
}

private fun getDrawingBounds(chartContext: ChartContext, size: Size): Viewport {
    val bounds = chartContext.viewport
    val xScaledRadius = size.width / 2 / chartContext.scaleX
    val yScaledRadius = size.height / 2 / chartContext.scaleY
    return Viewport(
        minX = bounds.minX - xScaledRadius,
        maxX = bounds.maxX + xScaledRadius,
        minY = bounds.minY - yScaledRadius,
        maxY = bounds.maxY + yScaledRadius
    )
}

fun circleBoundingShapeProvider() = PointBoundingShapeProvider { chartData, rendererData, size ->
    BoundingShape.Circle(
        data = chartData,
        labelAnchorX = rendererData.x,
        labelAnchorY = rendererData.y,
        center = Offset(rendererData.x, rendererData.y),
        radius = size.width / 2
    )
}