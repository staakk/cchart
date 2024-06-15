package io.github.staakk.cchart.data

/**
 * Data to be represented by the chart.
 */
data class Point<out T>(
    val x: Float,
    val y: Float,
    val isInRendererSpace: Boolean,
    val data: T,
)

fun pointOf(x: Number, y: Number) = Point(x.toFloat(), y.toFloat(), false, null)

