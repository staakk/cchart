package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.RenderedPoint
import io.github.staakk.cchart.data.Series
import kotlin.math.abs

private class BarRenderer(
    private val brushProvider: (String) -> Brush,
    private val preferredWidth: Float,
    private val minimalSpacing: Float = 10f,
    private val style: DrawStyle,
    private val alpha: Float,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
    private val isSameX: (Float, Float) -> Boolean = { a, b -> abs(a - b) < 0.01f },
) : SeriesRenderer {

    override fun DrawScope.render(
        context: RendererContext,
        series: List<Series>
    ): List<RenderedPoint> {
        val renderedPoints = mutableListOf<RenderedPoint>()
        val points = series.flatMap { s -> s.points.map { SeriesPoint(s.name, it) } }
        val groups = getGroups(points).sortedBy { it[0].x }
        val width = getBarWidth(groups, context)

        groups.forEach { group ->
            val groupSize = group.size
            group.forEachIndexed { index, point ->
                val unitOffset = -groupSize / 2 + index
                val halfWidth = width / 2f

                val x = context.dataToRendererCoordX(point.x) - unitOffset * width - (1 - groupSize % 2) * halfWidth
                val y = context.dataToRendererSizeY(point.y)

                drawRect(
                    brush = brushProvider(point.seriesName),
                    topLeft = Offset(x - halfWidth, context.dataToRendererCoordY(0f)),
                    size = Size(width, -context.dataToRendererSizeY(point.y)),
                    style = style,
                    alpha = alpha,
                    colorFilter = colorFilter,
                    blendMode = blendMode
                )

                renderedPoints += RenderedPoint(
                    seriesName = point.seriesName,
                    point = point.point,
                    x = x,
                    y = -y
                )
            }
        }
        return renderedPoints
    }

    private fun getBarWidth(groups: List<List<SeriesPoint>>, context: RendererContext): Float {
        val minXDistance = context.dataToRendererSizeX(groups.getMinXDistance())
        val maxItemsNo = groups.maxByOrNull { it.size }?.size ?: 1
        val pointWidth = maxItemsNo * preferredWidth
        return if (minXDistance - pointWidth < minimalSpacing) {
            (minXDistance - minimalSpacing) / maxItemsNo
        } else {
            preferredWidth
        }
    }

    private fun List<List<SeriesPoint>>.getMinXDistance() =
        windowed(2) { abs(it[0][0].x - it[1][0].x) }
            .minOrNull()
            ?: Float.MAX_VALUE

    private fun getGroups(points: List<SeriesPoint>): List<List<SeriesPoint>> {
        val groups = mutableListOf<List<SeriesPoint>>()
        val pointsLeft = mutableListOf(*points.toTypedArray())
        while (pointsLeft.isNotEmpty()) {
            val point = pointsLeft.removeFirst()
            val sameXPoints = pointsLeft.filter { isSameX(point.x, it.x) } + point
            groups.add(sameXPoints)
            pointsLeft.removeAll(sameXPoints)
        }
        return groups
    }
}

private data class SeriesPoint(
    val seriesName: String,
    val point: Point
) {
    val x: Float
        get() = point.x

    val y: Float
        get() = point.y
}

fun barRenderer(
    brushProvider: (String) -> Brush,
    preferredWidth: Float,
    minimalSpacing: Float = 10f,
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    isSameX: (Float, Float) -> Boolean = { a, b -> abs(a - b) < 0.01f },
): SeriesRenderer = BarRenderer(
    brushProvider,
    preferredWidth,
    minimalSpacing,
    style,
    alpha,
    colorFilter,
    blendMode,
    isSameX
)