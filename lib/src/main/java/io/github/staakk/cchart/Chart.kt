package io.github.staakk.cchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import io.github.staakk.cchart.axis.*
import io.github.staakk.cchart.data.*
import io.github.staakk.cchart.data.Viewport.Companion.getViewport
import io.github.staakk.cchart.data.Viewport.Companion.getViewportFromGroupedSeries
import io.github.staakk.cchart.grid.GridRenderer
import io.github.staakk.cchart.label.*
import io.github.staakk.cchart.renderer.*
import io.github.staakk.cchart.util.detectTransformGestures

/**
 * Creates chart for visualising data.
 *
 * @param modifier Modifier to apply to this layout node.
 * @param viewport The viewport for the chart. Should be expressed int the same coordinate
 * system as the data represented on the chart.
 * @param maxViewport The maximal viewport that can be displayed by this chart.
 * @param minViewportSize Minimal size of the viewport.
 * @param maxViewportSize Maximal size of the viewport.
 * @param content A block that describes the contents of the chart.
 */
@Composable
fun Chart(
    modifier: Modifier = Modifier,
    viewport: Viewport? = null,
    maxViewport: Viewport? = viewport,
    minViewportSize: Size? = viewport?.size,
    maxViewportSize: Size? = viewport?.size,
    enableZoom: Boolean = false,
    onClick: (Offset, Point) -> Unit = { _, _ -> },
    content: ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.content()

    val viewportState = remember {
        mutableStateOf(
            viewport
                ?: scope.series.keys.getViewport() + scope.groupedSeries.keys.getViewportFromGroupedSeries()
        )
    }
    Chart(
        modifier = modifier,
        viewport = viewportState,
        maxViewport = maxViewport ?: viewportState.value,
        minViewportSize = minViewportSize ?: viewportState.value.size,
        maxViewportSize = maxViewportSize ?: viewportState.value.size,
        enableZoom = enableZoom,
        onClick = onClick,
        scope = scope
    )
}

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    chartState: ChartState,
    maxViewport: Viewport = chartState.viewport,
    minViewportSize: Size = chartState.viewport.size,
    maxViewportSize: Size = chartState.viewport.size,
    enableZoom: Boolean = false,
    onClick: (Offset, Point) -> Unit = { _, _ -> },
    content: ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.content()

    Chart(
        modifier = modifier,
        viewport = chartState.viewportState,
        maxViewport = maxViewport,
        minViewportSize = minViewportSize,
        maxViewportSize = maxViewportSize,
        enableZoom = enableZoom,
        onClick = onClick,
        scope = scope
    )
}

@Composable
private fun Chart(
    modifier: Modifier = Modifier,
    viewport: MutableState<Viewport>,
    maxViewport: Viewport = viewport.value,
    minViewportSize: Size = viewport.value.size,
    maxViewportSize: Size = viewport.value.size,
    enableZoom: Boolean = false,
    onClick: (Offset, Point) -> Unit,
    scope: ChartScopeImpl
) {
    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier) {
        val renderedPoints = remember { mutableStateOf(listOf<BoundingShape>()) }
        val canvasSize = with(density) { Size(width = maxWidth.toPx(), height = maxHeight.toPx()) }
        val rendererContext = rendererContext(viewport.value, canvasSize)

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(viewport) {
                    detectTransformGestures { _, pan, zoom, direction ->
                        val current = if (enableZoom) {
                            viewport.value.applyZoom(
                                zoom,
                                direction,
                                minViewportSize,
                                maxViewportSize
                            )
                        } else {
                            viewport.value
                        }

                        val dx = -pan.x / rendererContext.scaleX
                        val dy = pan.y / rendererContext.scaleY
                        viewport.value = current.applyPan(dx, dy, maxViewport)
                    }
                }
                .pointerInput(renderedPoints) {
                    detectTapGestures { offset ->
                        val point = renderedPoints.value
                            .firstOrNull { it.contains(offset.copy(y = offset.y - canvasSize.height)) }
                            ?.point
                            ?: pointOf(
                                rendererContext.rendererToDataCoordX(offset.x),
                                rendererContext.rendererToDataCoordY(offset.y) + viewport.value.height
                            )
                        onClick(offset, point)
                    }
                }
        ) drawScope@{
            clipRect {
                translate(
                    0f,
                    size.height - rendererContext.dataToRendererCoordY(rendererContext.bounds.minY)
                ) {
                    scope.gridRenderers.forEach { renderer ->
                        with(renderer) { this@drawScope.render(rendererContext) }
                    }

                    val points = mutableListOf<BoundingShape>()
                    points += scope.series.flatMap { (series, renderer) ->
                        with(renderer) { this@drawScope.render(rendererContext, series) }
                    }
                    points += scope.groupedSeries.flatMap { (series, renderer) ->
                        with(renderer) { this@drawScope.render(rendererContext, series) }
                    }
                    renderedPoints.value = points
                }
            }

            scope.horizontalAxisRenderers.forEach {
                with(it) { this@drawScope.render(rendererContext) }
            }
            scope.verticalAxisRenderer.forEach {
                with(it) { this@drawScope.render(rendererContext) }
            }

            scope.horizontalLabelRenderers.forEach {
                with(it) { this@drawScope.render(rendererContext) }
            }
            scope.verticalLabelRenderers.forEach {
                with(it) { this@drawScope.render(rendererContext) }
            }
        }

        scope.dataLabels.forEach {
            DataLabels(
                modifier = Modifier,
                renderedShapes = renderedPoints.value,
                canvasSize = canvasSize,
                labelContent = it
            )
        }

        AnchoredViews(
            modifier = Modifier,
            canvasSize = canvasSize,
            anchors = scope.anchors.mapKeys {
                it.key to Offset(
                    x = rendererContext.dataToRendererCoordX(it.key.x),
                    y = rendererContext.dataToRendererCoordY(it.key.y)
                )
            }
        )
    }
}

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

    fun anchor(point: Point, content: @Composable AnchorScope.() -> Unit)

    fun dataLabels(content: @Composable AnchorScope.() -> Unit)
}

class ChartState(
    viewport: Viewport
) {
    internal val viewportState = mutableStateOf(viewport)

    val viewport: Viewport get() = viewportState.value
}

private class ChartScopeImpl : ChartScope {

    val horizontalAxisRenderers = mutableListOf<HorizontalAxisRenderer>()

    val verticalAxisRenderer = mutableListOf<VerticalAxisRenderer>()

    val horizontalLabelRenderers = mutableListOf<HorizontalLabelRenderer>()

    val verticalLabelRenderers = mutableListOf<VerticalLabelRenderer>()

    val gridRenderers: MutableList<GridRenderer> = mutableListOf()

    val series: MutableMap<Series, SeriesRenderer> = mutableMapOf()

    val groupedSeries: MutableMap<GroupedSeries, GroupedSeriesRenderer> = mutableMapOf()

    val anchors: MutableMap<Point, @Composable AnchorScope.() -> Unit> = mutableMapOf()

    val dataLabels: MutableList<@Composable AnchorScope.() -> Unit> = mutableListOf()

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

    override fun anchor(point: Point, content: @Composable AnchorScope.() -> Unit) {
        anchors[point] = content
    }

    override fun dataLabels(content: @Composable AnchorScope.() -> Unit) {
        dataLabels.add(content)
    }
}