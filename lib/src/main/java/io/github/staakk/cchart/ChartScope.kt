package io.github.staakk.cchart

import androidx.compose.runtime.Composable
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.bounds.BoundsProvider
import io.github.staakk.cchart.bounds.BoundsProvider.Companion.NoBounds
import io.github.staakk.cchart.renderer.PointsRenderer
import io.github.staakk.cchart.renderer.Renderer

/**
 * Receiver scope which is used by the [Chart].
 */
interface ChartScope {

    fun series(
        series: Series,
        drawer: PointsRenderer,
        boundsProvider: BoundsProvider = NoBounds,
    )

    fun feature(renderer: Renderer)

    /**
     * Adds composable view at the place represented by [point].
     */
    fun anchor(point: Point<*>, content: @Composable AnchorScope.() -> Unit)

    /**
     * Adds labels defined by [content] to the data on the chart.
     */
    fun dataLabels(content: @Composable AnchorScope.() -> Unit)
}

fun ChartScope.features(vararg renderers: Renderer) {
    renderers.forEach { feature(it) }
}

internal class ChartScopeImpl : ChartScope {

    val series = mutableMapOf<Series, Pair<PointsRenderer, BoundsProvider>>()

    val renderers = mutableListOf<Renderer>()

    val anchors = mutableMapOf<Point<*>, @Composable AnchorScope.() -> Unit>()

    val dataLabels = mutableListOf<@Composable AnchorScope.() -> Unit>()

    override fun feature(renderer: Renderer) {
        renderers += renderer
    }

    override fun series(
        series: Series,
        drawer: PointsRenderer,
        boundsProvider: BoundsProvider
    ) {
        this.series[series] = drawer to boundsProvider
    }

    override fun anchor(point: Point<*>, content: @Composable AnchorScope.() -> Unit) {
        anchors[point] = content
    }

    override fun dataLabels(content: @Composable AnchorScope.() -> Unit) {
        dataLabels.add(content)
    }
}