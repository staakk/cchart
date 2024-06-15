package io.github.staakk.cchart.data

/**
 * Series of data to be represented by the [io.github.staakk.cchart.Chart]
 *
 * @param data Data in this series.
 */
class Series(data: List<Point<*>>): List<Point<*>> by data

fun seriesOf(vararg data: Point<*>) = Series(data.toList())

fun seriesOf(vararg data: Pair<Number, Number>) =
    Series(
        data
            .toList()
            .map { (x, y) -> pointOf(x.toFloat(), y.toFloat()) }
    )