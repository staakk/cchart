package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Viewport
import kotlin.math.abs

fun barGroupRenderer(
    preferredWidth: Float,
    minimalSpacing: Float = 10f,
    draw: DrawScope.(index: Int, point: Point, topLeft: Offset, size: Size) -> RenderedShape = drawBar()
): GroupedSeriesRenderer = GroupedSeriesRenderer { context, series ->
    val renderedPoints = mutableListOf<RenderedShape>()
    val drawingBounds = getDrawingBounds(context)
    val groups = series.points
        .filter {
            if (it.isEmpty()) false
            else {
                val first = it.first()
                first.x in drawingBounds.minX..drawingBounds.maxX
            }
        }
    val width = getBarWidth(groups, context, preferredWidth, minimalSpacing)

    groups.forEach { group ->
        val groupSize = group.size
        group.forEachIndexed { index, point ->
            val unitOffset = -groupSize / 2 + index
            val halfWidth = width / 2f

            val x =
                context.dataToRendererCoordX(point.x) + unitOffset * width + (1 - groupSize % 2) * halfWidth
            val y = context.dataToRendererSizeY(point.y)

            val topLeft = Offset(x - halfWidth, context.dataToRendererCoordY(0f))
            val size = Size(width, -y)
            renderedPoints += draw(index, point, topLeft, size)
        }
    }
    renderedPoints
}

private fun getBarWidth(
    groups: List<List<Point>>,
    context: RendererContext,
    preferredWidth: Float,
    minimalSpacing: Float
): Float {
    val minXDistance = context.dataToRendererSizeX(groups.getMinXDistance())
    val maxItemsNo = groups.maxByOrNull { it.size }?.size ?: 1
    val pointWidth = maxItemsNo * preferredWidth
    return if (minXDistance - pointWidth < minimalSpacing) {
        (minXDistance - minimalSpacing) / maxItemsNo
    } else {
        preferredWidth
    }
}

private fun List<List<Point>>.getMinXDistance() =
    windowed(2) { abs(it[0][0].x - it[1][0].x) }
        .minOrNull()
        ?: Float.MAX_VALUE

private fun getDrawingBounds(rendererContext: RendererContext): Viewport {
    val canvasBounds = rendererContext.bounds
    val canvasWidth = rendererContext.canvasSize.width
    return Viewport(
        maxX = canvasBounds.maxX + canvasWidth / 2,
        minX = canvasBounds.minX - canvasWidth / 2,
        minY = Float.MIN_VALUE,
        maxY = Float.MAX_VALUE
    )
}

fun drawBar(
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    brushProvider: (index: Int, Point) -> Brush = { _, _ -> SolidColor(Color.Black) }
): DrawScope.(index: Int, point: Point, topLeft: Offset, size: Size) -> RenderedShape =
    { index: Int, point: Point, topLeft: Offset, size: Size ->
        drawRect(
            brush = brushProvider(index, point),
            topLeft = topLeft,
            size = size,
            style = style,
            alpha = alpha,
            colorFilter = colorFilter,
            blendMode = blendMode
        )
        RenderedShape.Rect(
            seriesName = "point.seriesName",
            point = point,
            labelAnchorX = topLeft.x + size.width / 2,
            labelAnchorY = size.height,
            topLeft = topLeft,
            bottomRight = Offset(topLeft.x + size.width, topLeft.y - size.height)
        )
    }