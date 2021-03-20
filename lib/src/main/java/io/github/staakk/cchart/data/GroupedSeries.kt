package io.github.staakk.cchart.data

data class GroupedSeries(
    val points: List<List<Point>>
)

fun groupedSeriesOf(vararg points: List<Point>) = GroupedSeries(points.toList())

fun groupedSeriesOf(vararg points: Point) = GroupedSeries(points.map(::listOf))
