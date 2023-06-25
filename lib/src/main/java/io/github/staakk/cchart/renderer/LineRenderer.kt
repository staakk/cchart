package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.*
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.dsl.PrimitiveStyle
import io.github.staakk.cchart.moveTo
import io.github.staakk.cchart.lineTo
import io.github.staakk.cchart.dsl.primitiveStyle

fun interface LineDrawer {

    /**
     * Draws the line based on provided [points].
     *
     * @param points Points to draw the line with.
     */
    fun RendererScope.draw(points: List<LinePoint>)
}

fun interface LineBoundingShapeProvider {

    /**
     * Provides bounding shapes used for data labels and on click listener of the
     * [io.github.staakk.cchart.Chart].
     *
     * @param points Points to provide [BoundingShape]s for.
     */
    fun RendererScope.provide(points: List<LinePoint>): List<BoundingShape>
}

data class LinePoint(
    val chartPoint: Data<*>,
    val rendererPoint: Data<*>
)

/**
 * Creates [SeriesRenderer] that renders a line.
 *
 * @param lineDrawer A function drawing the line.
 * @param boundingShapeProvider Provider of the [BoundingShape]s for the rendered line.
 */
fun lineRenderer(
    lineDrawer: LineDrawer = lineDrawer(PrimitiveStyle()),
    boundingShapeProvider: LineBoundingShapeProvider = lineBoundingShapeProvider()
) = SeriesRenderer { series ->
    val rendererScope = RendererScope(this, chartContext)
    if (series.size < 2) return@SeriesRenderer emptyList()
    series.getLineInViewport(chartContext.viewport)
        .map { LinePoint(it, it.toRendererData(chartContext)) }
        .let {
            with(rendererScope) {
                with(lineDrawer) { draw(it) }
                with(boundingShapeProvider) { provide(it) }
            }
        }
}

fun lineDrawer(primitiveStyleBuilder: PrimitiveStyle.() -> Unit) =
    lineDrawer(primitiveStyle(primitiveStyleBuilder))

fun lineDrawer(primitiveStyle: PrimitiveStyle) = LineDrawer { pointsToRender ->
    with(primitiveStyle) {
        drawPath(
            path = Path().apply {
                pointsToRender.windowed(2) {
                    moveTo(it[0].rendererPoint.toOffset())
                    lineTo(it[1].rendererPoint.toOffset())
                }
                close()
            },
            alpha = alpha,
            brush = brush,
            style = style,
            colorFilter = colorFilter,
            blendMode = blendMode
        )
    }
}

fun lineBoundingShapeProvider(radius: Float = 20f) = LineBoundingShapeProvider { points ->
    points.map {
        BoundingShape.Circle(
            data = it.chartPoint,
            labelAnchorX = it.rendererPoint.x,
            labelAnchorY = it.rendererPoint.y,
            center = it.rendererPoint.toOffset(),
            radius = radius
        )
    }
}