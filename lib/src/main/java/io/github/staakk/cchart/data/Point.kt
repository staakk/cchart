package io.github.staakk.cchart.data


/**
 * Point to be represented by the chart.
 */
data class Point(
    val x: Float,
    val y: Float,
    /**
     * Additional data that can be used for rendering extra info in labels.
     */
    val tag: Any?
)

fun pointOf(x: Number, y: Number, tag: Any? = null) = Point(x.toFloat(), y.toFloat(), tag)
