package io.github.staakk.cchart.data

import androidx.compose.ui.geometry.Offset


/**
 * Data to be represented by the chart.
 */
abstract class Data<out T : Data<T>> {

    abstract val x: Float

    abstract val y: Float

    abstract val isInRendererSpace: Boolean

    private val asOffset by lazy { Offset(x, y) }

    fun toOffset() = asOffset

    data class Point(
        override val x: Float,
        override val y: Float,
        override val isInRendererSpace: Boolean = false,
    ) : Data<Point>()

    data class PointWithError(
        override val x: Float,
        override val y: Float,
        val errorX: Float,
        val errorY: Float,
        override val isInRendererSpace: Boolean = false
    ) : Data<PointWithError>()
}

fun pointOf(x: Number, y: Number) = Data.Point(x.toFloat(), y.toFloat())

fun pointOf(x: Number, y: Number, errorX: Number, errorY: Number) =
    Data.PointWithError(x.toFloat(), y.toFloat(), errorX.toFloat(), errorY.toFloat())
