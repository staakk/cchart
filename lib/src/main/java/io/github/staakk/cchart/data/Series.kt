package io.github.staakk.cchart.data

import io.github.staakk.cchart.util.indexOfFirstFrom

/**
 * Series of data to be represented by the [io.github.staakk.cchart.Chart]
 *
 * @param points Data points in this series.
 */
data class Series(
    val points: List<Point>,
) {

    fun getPointsInViewport(viewport: Viewport) = points.filter(viewport::contains)

    fun getLineInViewport(viewport: Viewport): List<Point> {
        val result = mutableListOf<Point>()
        var index = 0
        while (index < points.size) {
            val startIndex = points.indexOfFirstFrom(index) { viewport.contains(it) }
            if (startIndex < 0) return result

            if (startIndex > 0) result += points[startIndex - 1]
            val nextOutsideBounds = points.indexOfFirstFrom(startIndex) { !viewport.contains(it) }
            val endIndex = if (nextOutsideBounds == -1) {
                index = points.size
                points.size
            } else {
                index = nextOutsideBounds
                nextOutsideBounds + 1
            }
            result.addAll(points.subList(startIndex, endIndex))
        }
        return result
    }
}

fun seriesOf(vararg points: Point) = Series(points.toList())