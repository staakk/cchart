package io.github.staakk.cchart.data


/**
 * Point to be represented by the chart.
 */
data class Point(
    val x: Float,
    val y: Float,
    val tag: Any?
)

fun pointOf(x: Number, y: Number, tag: Any? = null) = Point(x.toFloat(), y.toFloat(), tag)
