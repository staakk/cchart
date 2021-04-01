package io.github.staakk.cchart.data

class GroupedSeries(
    data: List<List<Data<*>>>
) : List<List<Data<*>>> by data

fun groupedSeriesOf(vararg data: List<Data<*>>) = GroupedSeries(data.toList())

fun groupedSeriesOf(vararg data: Data<*>) = GroupedSeries(data.map(::listOf))
