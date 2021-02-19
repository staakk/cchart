package io.github.staakk.composechart.data

import kotlin.math.abs

data class ChartData(
    val series: List<Series>
) {
    val maxXValue: Int
    val minXValue: Int
    val maxYValue: Int
    val minYValue: Int

    init {
        var maxX = Int.MIN_VALUE
        var minX = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        var minY = Int.MAX_VALUE

        series.forEach { s ->
            s.points.forEach { p ->
                if (p.x > maxX) maxX = p.x
                if (p.x < minX) minX = p.x
                if (p.y > maxY) maxY = p.y
                if (p.y < minY) minY = p.y
            }
        }

        maxXValue = maxX
        minXValue = minX
        maxYValue = maxY
        minYValue = minY
    }

    val xSpan = abs(maxXValue - minXValue)
    val ySpan = abs(maxYValue - minYValue)
}