package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Point

typealias LineDrawer = DrawScope.(List<Pair<Point, Offset>>) -> List<RenderedShape>

fun lineRenderer(lineDrawer: LineDrawer = drawLine()) = SeriesRenderer { context, series ->
    if (series.size < 2) return@SeriesRenderer emptyList()
    series.getLineInViewport(context.bounds)
        .map {
            it to Offset(
                x = context.dataToRendererCoordX(it.x),
                y = context.dataToRendererCoordY(it.y)
            )
        }
        .let { lineDrawer(it) }
}

fun drawLine(
    brush: Brush = SolidColor(Color.Black),
    style: DrawStyle = Stroke(width = 5f, cap = StrokeCap.Round),
    colorFilter: ColorFilter? = null,
    alpha: Float = 1.0f,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): LineDrawer = { pointsToRender ->
    val renderedPoints = mutableListOf<RenderedShape>()
    renderedPoints += RenderedShape.Circle(
        point = pointsToRender[0].first,
        labelAnchorX = pointsToRender[0].second.x,
        labelAnchorY = pointsToRender[0].second.y,
        center = pointsToRender[0].second,
        radius = 20f
    )
    drawPath(
        path = Path().apply {
            pointsToRender.windowed(2) {
                moveTo(it[0].second)
                lineTo(it[1].second)
                renderedPoints += RenderedShape.Circle(
                    point = it[1].first,
                    labelAnchorX = it[1].second.x,
                    labelAnchorY = it[1].second.y,
                    center = it[1].second,
                    radius = 20f
                )
            }
            close()
        },
        alpha = alpha,
        brush = brush,
        style = style,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
    renderedPoints
}

private fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
private fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)