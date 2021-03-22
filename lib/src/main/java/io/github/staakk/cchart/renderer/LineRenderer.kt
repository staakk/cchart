package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Point

typealias LineDrawer = DrawScope.(List<Pair<Point, Offset>>) -> Unit
typealias LineBoundingShapeDrawer = DrawScope.(List<Pair<Point, Offset>>) -> List<BoundingShape>

fun lineRenderer(
    lineDrawer: LineDrawer = drawLine(),
    lineBoundingShapeProvider: LineBoundingShapeDrawer = lineBoundingShapeProvider()
) = SeriesRenderer { context, series ->
    if (series.size < 2) return@SeriesRenderer emptyList()
    series.getLineInViewport(context.bounds)
        .map {
            it to Offset(
                x = context.dataToRendererCoordX(it.x),
                y = context.dataToRendererCoordY(it.y)
            )
        }
        .let {
            lineDrawer(it)
            lineBoundingShapeProvider(it)
        }
}

fun drawLine(
    brush: Brush = SolidColor(Color.Black),
    style: DrawStyle = Stroke(width = 5f, cap = StrokeCap.Round),
    colorFilter: ColorFilter? = null,
    alpha: Float = 1.0f,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): LineDrawer = { pointsToRender ->
    drawPath(
        path = Path().apply {
            pointsToRender.windowed(2) {
                moveTo(it[0].second)
                lineTo(it[1].second)
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

fun lineBoundingShapeProvider(radius: Float = 20f): LineBoundingShapeDrawer = { points ->
    points.map {
        BoundingShape.Circle(
            point = it.first,
            labelAnchorX = it.second.x,
            labelAnchorY = it.second.y,
            center = it.second,
            radius = radius
        )
    }
}

private fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
private fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)