package io.github.staakk.cchart

import androidx.compose.runtime.Composable
import io.github.staakk.cchart.axis.AxisRenderer
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.axis.axisDrawer
import io.github.staakk.cchart.axis.axisRenderer
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.grid.GridRenderer
import io.github.staakk.cchart.label.LabelRenderer
import io.github.staakk.cchart.renderer.BoundingShapeProvider
import io.github.staakk.cchart.renderer.Drawer
import io.github.staakk.cchart.renderer.NoBoundingShape
import io.github.staakk.cchart.style.LineStyle

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

    fun axis(axisRenderer: AxisRenderer)

    fun label(labelRenderer: LabelRenderer)

    fun series(
        series: Series,
        drawer: Drawer,
        boundingShapeProvider: BoundingShapeProvider = NoBoundingShape,
    )

    /**
     * Adds composable view at the place represented by [data].
     */
    fun anchor(data: Data<*>, content: @Composable AnchorScope.() -> Unit)

    /**
     * Adds labels defined by [content] to the data on the chart.
     */
    fun dataLabels(content: @Composable AnchorScope.() -> Unit)
}

/**
 * Adds [axisRenderer] for this chart. Calling this function multiple times results in
 * multiple renderers being added.
 */
fun ChartScope.horizontalAxis(positionPercent: Float = 0f, lineStyle: LineStyle = LineStyle()) {
    axis(
        axisRenderer(
            axisOrientation = AxisOrientation.Horizontal,
            positionPercent = positionPercent,
            axisDrawer = axisDrawer(lineStyle),
        )
    )
}

/**
 * Adds [axisRenderer] for this chart. Calling this function multiple times results in
 * multiple renderers being added.
 */
fun ChartScope.verticalAxis(positionPercent: Float = 0f, lineStyle: LineStyle = LineStyle()) {
    axis(
        axisRenderer(
            axisOrientation = AxisOrientation.Vertical,
            positionPercent = positionPercent,
            axisDrawer = axisDrawer(lineStyle),
        )
    )
}


internal class ChartScopeImpl : ChartScope {

    val newSeries = mutableMapOf<Series, Pair<Drawer, BoundingShapeProvider>>()

    val gridRenderers = mutableListOf<GridRenderer>()

    val axisRenderers = mutableListOf<AxisRenderer>()

    val labelsRenderers = mutableListOf<LabelRenderer>()

    val anchors = mutableMapOf<Data<*>, @Composable AnchorScope.() -> Unit>()

    val dataLabels = mutableListOf<@Composable AnchorScope.() -> Unit>()

    override fun grid(gridRenderer: GridRenderer) {
        gridRenderers.add(gridRenderer)
    }

    override fun axis(axisRenderer: AxisRenderer) {
        axisRenderers += axisRenderer
    }

    override fun label(labelRenderer: LabelRenderer) {
        labelsRenderers += labelRenderer
    }

    override fun series(
        series: Series,
        drawer: Drawer,
        boundingShapeProvider: BoundingShapeProvider
    ) {
        newSeries[series] = drawer to boundingShapeProvider
    }

    override fun anchor(data: Data<*>, content: @Composable AnchorScope.() -> Unit) {
        anchors[data] = content
    }

    override fun dataLabels(content: @Composable AnchorScope.() -> Unit) {
        dataLabels.add(content)
    }
}