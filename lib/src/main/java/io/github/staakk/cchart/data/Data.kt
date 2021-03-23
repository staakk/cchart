package io.github.staakk.cchart.data


/**
 * Data to be represented by the chart.
 */
sealed class Data {

    abstract val x: Float

    abstract val y: Float

    /**
     * Additional data.
     */
    abstract val tag: Any?

    data class Point(
        override val x: Float,
        override val y: Float,
        override val tag: Any?
    ) : Data()
}

fun pointOf(x: Number, y: Number, tag: Any? = null) = Data.Point(x.toFloat(), y.toFloat(), tag)
