package io.github.staakk.cchart.data


/**
 * Data to be represented by the chart.
 */
interface Data {

    val x: Float

    val y: Float

    data class Point(
        override val x: Float,
        override val y: Float,
    ) : Data
}

fun pointOf(x: Number, y: Number) = Data.Point(x.toFloat(), y.toFloat())
