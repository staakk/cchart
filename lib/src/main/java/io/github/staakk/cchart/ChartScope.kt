package io.github.staakk.cchart

import androidx.compose.runtime.Composable
import io.github.staakk.cchart.axis.HorizontalAxisRenderer
import io.github.staakk.cchart.axis.VerticalAxisRenderer
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.GroupedSeries
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.grid.GridRenderer
import io.github.staakk.cchart.label.HorizontalLabelRenderer
import io.github.staakk.cchart.label.VerticalLabelRenderer
import io.github.staakk.cchart.renderer.GroupedSeriesRenderer
import io.github.staakk.cchart.renderer.SeriesRenderer

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

    /**
     * Adds [axisRenderer] for this chart. Calling this function multiple times results in
     * multiple renderers being added.
     */
    fun horizontalAxis(axisRenderer: HorizontalAxisRenderer = horizontalAxisRenderer())

    /**
     * Adds [axisRenderer] for this chart. Calling this function multiple times results in
     * multiple renderers being added.
     */
    fun verticalAxis(axisRenderer: VerticalAxisRenderer = verticalAxisRenderer())

    /**
     * Adds [labelRenderer] for this chart. Calling this function multiple times results in
     * multiple renderers being added.
     */
    fun horizontalAxisLabels(labelRenderer: HorizontalLabelRenderer)

    /**
     * Adds [labelRenderer] for this chart. Calling this function multiple times results in
     * multiple renderers being added.
     */
    fun verticalAxisLabels(labelRenderer: VerticalLabelRenderer)

    /**
     * Adds series of data to the chart.
     */
    fun series(series: Series, renderer: SeriesRenderer)

    /**
     * Adds series of data to the chart.
     */
    fun series(series: GroupedSeries, renderer: GroupedSeriesRenderer)

    /**
     * Adds composable view at the place represented by [data].
     */
    fun anchor(data: Data<*>, content: @Composable AnchorScope.() -> Unit)

    /**
     * Adds labels defined by [content] to the data on the chart.
     */
    fun dataLabels(content: @Composable AnchorScope.() -> Unit)
}


internal class ChartScopeImpl : ChartScope {

    val horizontalAxisRenderers = mutableListOf<HorizontalAxisRenderer>()

    val verticalAxisRenderer = mutableListOf<VerticalAxisRenderer>()

    val horizontalLabelRenderers = mutableListOf<HorizontalLabelRenderer>()

    val verticalLabelRenderers = mutableListOf<VerticalLabelRenderer>()

    val gridRenderers = mutableListOf<GridRenderer>()

    val series = mutableMapOf<Series, SeriesRenderer>()

    val groupedSeries = mutableMapOf<GroupedSeries, GroupedSeriesRenderer> ()

    val anchors = mutableMapOf<Data<*>, @Composable AnchorScope.() -> Unit>()

    val dataLabels = mutableListOf<@Composable AnchorScope.() -> Unit>()

    override fun grid(gridRenderer: GridRenderer) {
        gridRenderers.add(gridRenderer)
    }

    override fun horizontalAxis(axisRenderer: HorizontalAxisRenderer) {
        horizontalAxisRenderers.add(axisRenderer)
    }

    override fun verticalAxis(axisRenderer: VerticalAxisRenderer) {
        verticalAxisRenderer.add(axisRenderer)
    }

    override fun horizontalAxisLabels(labelRenderer: HorizontalLabelRenderer) {
        horizontalLabelRenderers.add(labelRenderer)
    }

    override fun verticalAxisLabels(labelRenderer: VerticalLabelRenderer) {
        verticalLabelRenderers.add(labelRenderer)
    }

    override fun series(series: Series, renderer: SeriesRenderer) {
        this.series[series] = renderer
    }

    override fun series(series: GroupedSeries, renderer: GroupedSeriesRenderer) {
        groupedSeries[series] = renderer
    }

    override fun anchor(data: Data<*>, content: @Composable AnchorScope.() -> Unit) {
        anchors[data] = content
    }

    override fun dataLabels(content: @Composable AnchorScope.() -> Unit) {
        dataLabels.add(content)
    }
}