package io.github.staakk.cchart.data

class GroupedSeries(
    points: List<List<Point>>
) : List<List<Point>> by points

fun groupedSeriesOf(vararg points: List<Point>) = GroupedSeries(points.toList())

fun groupedSeriesOf(vararg points: Point) = GroupedSeries(points.map(::listOf))
