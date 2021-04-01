package io.github.staakk.cchart.data

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.renderer.ChartContext


/**
 * Data to be represented by the chart.
 */
abstract class Data<out T : Data<T>> {

    abstract val x: Float

    abstract val y: Float

    abstract val isInRendererSpace: Boolean

    private val asOffset by lazy { Offset(x, y) }

    abstract fun toRendererData(chartContext: ChartContext): T

    abstract fun toChartData(chartContext: ChartContext): T

    fun toOffset() = asOffset

    data class Point(
        override val x: Float,
        override val y: Float,
        override val isInRendererSpace: Boolean = false,
    ) : Data<Point>() {

        override fun toRendererData(chartContext: ChartContext): Point {
            require(!isInRendererSpace) { "Data is already in renderer space." }
            return Point(
                x = chartContext.toRendererX(x),
                y = chartContext.toRendererY(y),
                true
            )
        }


        override fun toChartData(chartContext: ChartContext): Point {
            require(isInRendererSpace) { "Data is already in chart space." }
            return Point(
                x = chartContext.toChartX(x),
                y = chartContext.toChartY(y),
                false
            )
        }
    }

    data class PointWithError(
        override val x: Float,
        override val y: Float,
        val errorX: Float,
        val errorY: Float,
        override val isInRendererSpace: Boolean = false
    ) : Data<PointWithError>() {

        override fun toRendererData(chartContext: ChartContext): PointWithError {
            require(!isInRendererSpace) { "Data is already in renderer space." }
            return PointWithError(
                x = chartContext.toRendererY(x),
                y = chartContext.toRendererX(y),
                isInRendererSpace = true,
                errorX = chartContext.toRendererWidth(errorX),
                errorY = chartContext.toRendererHeight(errorY)
            )
        }


        override fun toChartData(chartContext: ChartContext): PointWithError {
            require(isInRendererSpace) { "Data is already in chart space." }
            return PointWithError(
                x = chartContext.toChartX(x),
                y = chartContext.toChartY(y),
                isInRendererSpace = false,
                errorX = chartContext.toChartWidth(errorX),
                errorY = chartContext.toChartHeight(errorY)
            )
        }
    }

}

fun pointOf(x: Number, y: Number) = Data.Point(x.toFloat(), y.toFloat())

fun pointOf(x: Number, y: Number, errorX: Number, errorY: Number) =
    Data.PointWithError(x.toFloat(), y.toFloat(), errorX.toFloat(), errorY.toFloat())
