package io.github.staakk.cchart

import androidx.compose.runtime.Composable
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.grid.GridRenderer
import io.github.staakk.cchart.label.LabelRenderer
import io.github.staakk.cchart.renderer.BoundingShapeProvider
import io.github.staakk.cchart.renderer.NoBoundingShape
import io.github.staakk.cchart.renderer.PointsRenderer
import io.github.staakk.cchart.renderer.Renderer

/**
 * Receiver scope which is used by the [Chart].
 */
interface ChartScope {

    /**
     * Adds the [gridRenderer] for this chart. Calling this function multiple times results in
     * multiple renderers being added.
     *
     * @param gridRenderer Renderer to be set.
     *
     * @see [io.github.staakk.cchart.grid.gridRenderer]
     */
    fun grid(gridRenderer: GridRenderer)

    fun label(labelRenderer: LabelRenderer)

    fun series(
        series: Series,
        drawer: PointsRenderer,
        boundingShapeProvider: BoundingShapeProvider = NoBoundingShape,
    )

    fun feature(renderer: Renderer)

    /**
     * Adds composable view at the place represented by [data].
     */
    fun anchor(data: Data<*>, content: @Composable AnchorScope.() -> Unit)

    /**
     * Adds labels defined by [content] to the data on the chart.
     */
    fun dataLabels(content: @Composable AnchorScope.() -> Unit)
}

fun ChartScope.features(vararg renderers: Renderer) {
    renderers.forEach { feature(it) }
}

internal class ChartScopeImpl : ChartScope {

    val series = mutableMapOf<Series, Pair<PointsRenderer, BoundingShapeProvider>>()

    val gridRenderers = mutableListOf<GridRenderer>()

    val labelsRenderers = mutableListOf<LabelRenderer>()

    val renderers = mutableListOf<Renderer>()

    val anchors = mutableMapOf<Data<*>, @Composable AnchorScope.() -> Unit>()

    val dataLabels = mutableListOf<@Composable AnchorScope.() -> Unit>()

    override fun grid(gridRenderer: GridRenderer) {
        gridRenderers.add(gridRenderer)
    }

    override fun label(labelRenderer: LabelRenderer) {
        labelsRenderers += labelRenderer
    }

    override fun feature(renderer: Renderer) {
        renderers += renderer
    }

    override fun series(
        series: Series,
        drawer: PointsRenderer,
        boundingShapeProvider: BoundingShapeProvider
    ) {
        this.series[series] = drawer to boundingShapeProvider
    }

    override fun anchor(data: Data<*>, content: @Composable AnchorScope.() -> Unit) {
        anchors[data] = content
    }

    override fun dataLabels(content: @Composable AnchorScope.() -> Unit) {
        dataLabels.add(content)
    }
}