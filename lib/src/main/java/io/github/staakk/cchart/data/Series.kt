package io.github.staakk.cchart.data

import io.github.staakk.cchart.util.indexOfFirstFrom

/**
 * Series of data to be represented by the [io.github.staakk.cchart.Chart]
 *
 * @param points Data points in this series.
 */
class Series(
    points: List<Point>,
): List<Point> by points {

    fun getPointsInViewport(viewport: Viewport) = filter(viewport::contains)

    fun getLineInViewport(viewport: Viewport): List<Point> {
        val result = mutableListOf<Point>()
        var index = 0
        while (index < size) {
            val startIndex = indexOfFirstFrom(index) { viewport.contains(it) }
            if (startIndex < 0) return result

            if (startIndex > 0) result += get(startIndex - 1)
            val nextOutsideBounds = indexOfFirstFrom(startIndex) { !viewport.contains(it) }
            val endIndex = if (nextOutsideBounds == -1) {
                index = size
                size
            } else {
                index = nextOutsideBounds
                nextOutsideBounds + 1
            }
            result.addAll(subList(startIndex, endIndex))
        }
        return result
    }
}

fun seriesOf(vararg points: Point) = Series(points.toList())