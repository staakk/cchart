package io.github.staakk.cchart.data

data class Series(
    val name: String,
    val points: List<Point>,
)

fun seriesOf(name: String, vararg points: Point) = Series(name, points.toList())