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
    content: @Composable ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.content()

    val viewportState = remember {
        mutableStateOf(viewport ?: scope.series.keys.getViewport())
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
    content: @Composable ChartScope.() -> Unit
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
    val leftLabelSize = scope.verticalLabelRenderers.getMinLabelMaxSize()
    val rightLabelSize = scope.verticalLabelRenderers.getMaxLabelMaxSize()
    val topLabelSize = scope.horizontalLabelRenderers.getMinLabelMaxSize()
    val bottomLabelSize = scope.horizontalLabelRenderers.getMaxLabelMaxSize()

    val density = LocalDensity.current
    val paddingValues = with(density) {
        PaddingValues(
            start = leftLabelSize.width.toDp(),
            end = rightLabelSize.width.toDp(),
            top = topLabelSize.height.toDp(),
            bottom = bottomLabelSize.height.toDp(),
        )
    }

    BoxWithConstraints(modifier = modifier.then(Modifier.padding(paddingValues))) {
        val renderedPoints = remember { mutableStateOf(listOf<RenderedShape>()) }
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
                        renderedPoints.value
                            .firstOrNull {
                                it.contains(offset.copy(y = offset.y - canvasSize.height))
                            }
                            ?.let { onClick(offset, it.point) }
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

                    val points = mutableListOf<RenderedShape>()
                    points += scope.series.flatMap { (series, renderer) ->
                        with(renderer) { this@drawScope.render(rendererContext, series) }
                    }
                    points += scope.groupedSeries.flatMap { (series, renderer) ->
                        with(renderer) { this@drawScope.render(rendererContext, series)}
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
                modifier = modifier,
                renderedShapes = renderedPoints.value,
                canvasSize = canvasSize,
                horizontalAlignment = it.horizontalAlignment,
                verticalAlignment = it.verticalAlignment,
                labelContent = it.labelContent
            )
        }
    }
}

private fun List<LabelRenderer>.getMinLabelMaxSize() = filter { it.getNormalisedPosition() < 0.5f }
    .minByOrNull { it.getNormalisedPosition() }
    ?.getMaxLabelSize() ?: Size.Zero

private fun List<LabelRenderer>.getMaxLabelMaxSize() = filter { it.getNormalisedPosition() > 0.5f }
    .maxByOrNull { it.getNormalisedPosition() }
    ?.getMaxLabelSize() ?: Size.Zero

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
     * Adds labels to the data with specified [horizontalAlignment] and [verticalAlignment].
     */
    fun dataLabels(
        horizontalAlignment: HorizontalAlignment = HorizontalAlignment.CENTER,
        verticalAlignment: VerticalAlignment = VerticalAlignment.TOP,
        labelContent: @Composable DataLabelScope.() -> Unit
    )
}

class ChartState(
    viewport: Viewport
) {
    internal val viewportState = mutableStateOf(viewport)

    val viewport: Viewport get() = viewportState.value
}

private data class DataLabel(
    val horizontalAlignment: HorizontalAlignment,
    val verticalAlignment: VerticalAlignment,
    val labelContent: @Composable DataLabelScope.() -> Unit
)

private class ChartScopeImpl : ChartScope {

    val horizontalAxisRenderers = mutableListOf<HorizontalAxisRenderer>()

    val verticalAxisRenderer = mutableListOf<VerticalAxisRenderer>()

    val horizontalLabelRenderers = mutableListOf<HorizontalLabelRenderer>()

    val verticalLabelRenderers = mutableListOf<VerticalLabelRenderer>()

    val gridRenderers: MutableList<GridRenderer> = mutableListOf()

    val series: MutableMap<Series, SeriesRenderer> = mutableMapOf()

    val groupedSeries: MutableMap<GroupedSeries, GroupedSeriesRenderer> = mutableMapOf()

    val dataLabels: MutableList<DataLabel> = mutableListOf()

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

    override fun dataLabels(
        horizontalAlignment: HorizontalAlignment,
        verticalAlignment: VerticalAlignment,
        labelContent: @Composable DataLabelScope.() -> Unit
    ) {
        val dataLabel = DataLabel(
            horizontalAlignment = horizontalAlignment,
            verticalAlignment = verticalAlignment,
            labelContent = labelContent
        )
        dataLabels.add(dataLabel)
    }
}