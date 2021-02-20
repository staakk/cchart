package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Series
import kotlin.math.abs

class BarRenderer(
    private val brushProvider: (String) -> Brush,
    private val preferredWidth: Float,
    private val minimalSpacing: Float = 10f,
    private val isSameX: (Float, Float) -> Boolean = { a, b -> abs(a - b) < 0.01f },
) : SeriesRenderer {

    override fun DrawScope.render(context: RendererContext, series: List<Series>) {
        val points = series.flatMap { s -> s.points.map { SeriesPoint(s.name, it) } }
        val groups = getGroups(points).sortedBy { it[0].x }
        val width = getBarWidth(groups, context)

        groups.forEach { group ->
            val groupSize = group.size
            group.forEachIndexed { index, point ->
                val unitOffset = -groupSize / 2 + index
                drawRect(
                    brush = brushProvider(point.seriesName),
                    topLeft = Offset(
                        context.dataToRendererCoordX(point.x) - unitOffset * width - (1 + (1 - groupSize % 2)) * width / 2f,
                        context.dataToRendererCoordY(0f)
                    ),
                    size = Size(width, context.dataToRendererSizeY(point.y))
                )
            }
        }
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