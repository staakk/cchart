package io.github.staakk.cchart.data

/**
 * Series of data to be represented by the [io.github.staakk.cchart.Chart]
 *
 * @param data Data in this series.
 */
class Series(
    data: List<Data<*>>,
): List<Data<*>> by data

fun seriesOf(vararg data: Data<*>) = Series(data.toList())

fun seriesOf(vararg data: Pair<Number, Number>) =
    Series(
        data
            .toList()
            .map { (x, y) -> Data.Point(x.toFloat(), y.toFloat()) }
    )