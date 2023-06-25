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
import io.github.staakk.cchart.dsl.PrimitiveStyle

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
    pointDrawer: PointDrawer = circleDrawer(PrimitiveStyle()),
    boundingShapeProvider: PointBoundingShapeProvider = circleBoundingShapeProvider()
) = SeriesRenderer { series ->
    series.getPointsInViewport(getDrawingBounds(chartContext, size))
        .map { point ->
            val rendererData = point.toRendererData(chartContext)
            with(pointDrawer) { draw(point, rendererData, size) }
            with(boundingShapeProvider) { provide(point, rendererData, size) }
        }
}
fun circleDrawer(builder: PrimitiveStyle.() -> Unit) = circleDrawer(PrimitiveStyle().apply(builder))

fun circleDrawer(primitiveStyle: PrimitiveStyle) = PointDrawer { _, rendererData, size ->
    // TODO change to drawOval
    with(primitiveStyle) {
        drawCircle(
            radius = size.height / 2,
            center = Offset(rendererData.x, rendererData.y),
            alpha = alpha,
            brush = brush,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode,
        )
    }
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