package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.style.PrimitiveStyle
import io.github.staakk.cchart.moveTo
import io.github.staakk.cchart.lineTo

fun interface LineDrawer {

    /**
     * Draws the line based on provided [points].
     *
     * @param points Points to draw the line with.
     */
    fun RendererScope.draw(points: List<RendererPoint<*>>)
}

fun interface LineBoundingShapeProvider {

    /**
     * Provides bounding shapes used for data labels and on click listener of the
     * [io.github.staakk.cchart.Chart].
     *
     * @param points Points to provide [BoundingShape]s for.
     */
    fun RendererScope.provide(points: List<RendererPoint<*>>): List<BoundingShape>
}

fun defaultLineStyle() = PrimitiveStyle(style = Stroke(width = 5f, cap = StrokeCap.Square))

/**
 * Creates [SeriesRenderer] that renders a line.
 *
 * @param lineDrawer A function drawing the line.
 * @param boundingShapeProvider Provider of the [BoundingShape]s for the rendered line.
 */
fun lineRenderer(
    lineDrawer: LineDrawer = lineDrawer(defaultLineStyle()),
    boundingShapeProvider: LineBoundingShapeProvider = lineBoundingShapeProvider()
) = SeriesRenderer { series ->
    val rendererScope = RendererScope(this, chartContext)
    if (series.size < 2) return@SeriesRenderer emptyList()
    series.getLineInViewport(chartContext.viewport)
        .map {it.toRendererPoint(chartContext) }
        .let {
            with(rendererScope) {
                with(lineDrawer) { draw(it) }
                with(boundingShapeProvider) { provide(it) }
            }
        }
}

fun lineDrawer(primitiveStyleBuilder: PrimitiveStyle.() -> Unit) =
    lineDrawer(defaultLineStyle().apply(primitiveStyleBuilder))

fun lineDrawer(primitiveStyle: PrimitiveStyle) = LineDrawer { pointsToRender ->
    with(primitiveStyle) {
        drawPath(
            path = Path().apply {
                pointsToRender.windowed(2) {
                    moveTo(it[0].toOffset())
                    lineTo(it[1].toOffset())
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
            data = it.data,
            labelAnchorX = it.x,
            labelAnchorY = it.y,
            center = it.toOffset(),
            radius = radius
        )
    }
}