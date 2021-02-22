package io.github.staakk.cchart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import io.github.staakk.cchart.axis.*
import io.github.staakk.cchart.data.*
import io.github.staakk.cchart.data.DataBounds.Companion.getBounds
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.GridRenderer
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.*
import io.github.staakk.cchart.renderer.*

/**
 * Creates chart for visualising data.
 *
 * @param modifier Modifier to apply to this layout node.
 * @param bounds The initial bounds for the layout. Should be expressed int the same coordinate
 * system as the data series provided for the chart via [ChartScope.series].
 * @param panRange Pan range expressed in pixels.
 * @param zoomRange Zoom range.
 * @param content A black that describes the contents of the chart.
 */
@Composable
fun Chart(
    modifier: Modifier = Modifier,
    bounds: DataBounds? = null,
    panRange: PanRange = PanRange.NoPan,
    zoomRange: ClosedFloatingPointRange<Float> = ZoomRange.None,
    content: @Composable ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.content()

    val dataBounds = scope.series.keys
        .flatten()
        .getBounds()

    val density = LocalDensity.current
    val panState = remember { mutableStateOf(Offset(0f, 0f)) }
    val zoomState = remember { mutableStateOf(1f) }

    val leftLabelSize = scope.verticalLabelRenderers.getMinLabelMaxSize()
    val rightLabelSize = scope.verticalLabelRenderers.getMaxLabelMaxSize()
    val topLabelSize = scope.horizontalLabelRenderers.getMinLabelMaxSize()
    val bottomLabelSize = scope.horizontalLabelRenderers.getMaxLabelMaxSize()

    val paddingValues = PaddingValues(
        start = with(density) { leftLabelSize.width.toDp() },
        end = with(density) { rightLabelSize.width.toDp() },
        top = with(density) { topLabelSize.height.toDp() },
        bottom = with(density) { bottomLabelSize.height.toDp() },
    )

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .pointerInput(panState) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        panState.value = (panState.value + pan).coerceIn(panRange)
                        zoomState.value = (zoomState.value * zoom).coerceIn(zoomRange)
                    }
                }
        ) drawScope@{
            val dataAdjustedPan = panState.value.let {
                Offset(
                    -it.x / (size.width / dataBounds.width),
                    it.y / (size.height / dataBounds.height)
                )
            }

            val rendererContext = rendererContext(
                ((bounds ?: dataBounds) + dataAdjustedPan).withZoom(zoomState.value),
                size
            )

            clipRect {
                translate(
                    0f,
                    size.height - rendererContext.dataToRendererCoordY(rendererContext.bounds.minY)
                ) {
                    scope.gridRenderers.forEach { renderer ->
                        with(renderer) { this@drawScope.render(rendererContext) }
                    }

                    scope.series.forEach { (series, renderer) ->
                        renderer.apply { this@drawScope.render(rendererContext, series) }
                    }
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
    fun horizontalLabel(labelRenderer: HorizontalLabelRenderer)

    /**
     * Adds [labelRenderer] for this chart. Calling this function multiple times results in
     * multiple renderers being added.
     */
    fun verticalLabel(labelRenderer: VerticalLabelRenderer)

    /**
     * Adds series of data to the chart.
     */
    fun series(vararg series: Series, renderer: SeriesRenderer)
}

private class ChartScopeImpl : ChartScope {

    var horizontalAxisRenderers = mutableListOf<HorizontalAxisRenderer>()

    var verticalAxisRenderer = mutableListOf<VerticalAxisRenderer>()

    var horizontalLabelRenderers = mutableListOf<HorizontalLabelRenderer>()

    var verticalLabelRenderers = mutableListOf<VerticalLabelRenderer>()

    var gridRenderers: MutableList<GridRenderer> = mutableListOf()

    val series: MutableMap<List<Series>, SeriesRenderer> = mutableMapOf()

    override fun grid(gridRenderer: GridRenderer) {
        gridRenderers.add(gridRenderer)
    }

    override fun horizontalAxis(axisRenderer: HorizontalAxisRenderer) {
        horizontalAxisRenderers.add(axisRenderer)
    }

    override fun verticalAxis(axisRenderer: VerticalAxisRenderer) {
        verticalAxisRenderer.add(axisRenderer)
    }

    override fun horizontalLabel(labelRenderer: HorizontalLabelRenderer) {
        horizontalLabelRenderers.add(labelRenderer)
    }

    override fun verticalLabel(labelRenderer: VerticalLabelRenderer) {
        verticalLabelRenderers.add(labelRenderer)
    }

    override fun series(vararg series: Series, renderer: SeriesRenderer) {
        this.series[series.toList()] = renderer
    }
}

@Preview(name = "Chart")
@Composable
private fun PreviewCoordinatePlane() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Chart(
                modifier = Modifier.weight(1f),
                bounds = DataBounds(-1f, 5f, 0f, 9f)
            ) {
                series(
                    seriesOf(
                        "First",
                        pointOf(0f, 1f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    renderer = pointRenderer(SolidColor(Color.Blue), 10f)
                )
                series(
                    seriesOf(
                        "Second",
                        pointOf(0f, 0f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    renderer = lineRenderer(SolidColor(Color.Green))
                )
                series(
                    seriesOf(
                        "Third",
                        pointOf(0f, 1f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    renderer = barRenderer({ SolidColor(Color.Red) }, 15f)
                )

                grid(
                    gridRenderer(
                        orientation = GridOrientation.VERTICAL,
                        gridLinesProvider = { min, max ->
                            (min.toInt()..max.toInt()).map { it }
                                .filter { it % 2 == 1 }
                                .map { it.toFloat() }
                        },
                        alpha = 1.0f
                    )
                )
                grid(gridRenderer(orientation = GridOrientation.HORIZONTAL))

                horizontalAxis(horizontalAxisRenderer())

                horizontalLabel(horizontalLabelRenderer())

                verticalAxis(verticalAxisRenderer())

                verticalLabel(verticalLabelRenderer())

                horizontalLabel(
                    horizontalLabelRenderer(
                        location = HorizontalLabelLocation.TOP,
                        side = HorizontalLabelSide.ABOVE
                    )
                )

            }

            Text(modifier = Modifier.weight(1f), text = "Another fine chart")
        }
    }
}