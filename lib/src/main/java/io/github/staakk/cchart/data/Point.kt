package io.github.staakk.cchart.data


/**
 * Point to be represented by the chart.
 */
data class Point(
    val x: Float,
    val y: Float
)

fun pointOf(x: Number, y: Number) = Point(x.toFloat(), y.toFloat())
