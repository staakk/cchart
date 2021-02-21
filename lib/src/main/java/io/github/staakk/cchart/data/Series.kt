package io.github.staakk.cchart.data

/**
 * Series of data to be represented by the [io.github.staakk.cchart.Chart]
 *
 * @param name Name of this series.
 * @param points Data points in this series.
 */
data class Series(
    val name: String,
    val points: List<Point>,
)

fun seriesOf(name: String, vararg points: Point) = Series(name, points.toList())