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
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.axis.AxisRenderer
import io.github.staakk.cchart.axis.XAxisRenderer
import io.github.staakk.cchart.axis.YAxisRenderer
import io.github.staakk.cchart.data.*
import io.github.staakk.cchart.renderer.*
import io.github.staakk.cchart.util.OffsetRange
import io.github.staakk.cchart.util.coerceIn

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    bounds: DataBounds? = null,
    panRange: OffsetRange = OffsetRange(0f..0f, 0f..0f),
    zoomRange: ClosedFloatingPointRange<Float> = 0.5f..1.5f,
    content: ChartScope.() -> Unit
) {
    val scope = ChartScopeImpl()
    scope.apply(content)

    val dataBounds = scope.data.bounds

    val density = LocalDensity.current
    val panState = remember { mutableStateOf(Offset(0f, 0f)) }
    val zoomState = remember { mutableStateOf(1f) }

    val padding = 32.dp
    val paddingPx = with(density) { padding.toPx() }
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                density,
                Size(width = size.width, height = size.height)
            )

            clipRect {
                translate(
                    0f,
                    size.height - rendererContext.dataToRendererCoordY(rendererContext.bounds.minY)
                ) {
                    scope.data
                        .series
                        .forEach { series ->
                            (scope.seriesRenderers[series.name] ?: scope.renderer)
                                .apply { this@drawScope.render(rendererContext, series) }
                        }
                }
            }

            clipRect(top = -paddingPx, bottom = size.height + paddingPx) {
                with(scope.drawXAxis) { this@drawScope.renderLabels(rendererContext) }
            }
            clipRect(left = -paddingPx, right = size.width + paddingPx) {
                with(scope.drawYAxis) { this@drawScope.renderLabels(rendererContext) }
            }

            with(scope.drawXAxis) { this@drawScope.renderAxis(rendererContext) }
            with(scope.drawYAxis) { this@drawScope.renderAxis(rendererContext) }
        }
    }
}

interface ChartScope {

    fun xAxis(axisRenderer: AxisRenderer)

    fun yAxis(axisRenderer: AxisRenderer)

    fun data(vararg series: Series)

    fun renderer(renderer: SeriesRenderer)

    fun seriesRendererFor(vararg seriesName: String, renderer: SeriesRenderer)
}

private class ChartScopeImpl : ChartScope {

    var drawXAxis: AxisRenderer = XAxisRenderer(SolidColor(Color.Black), strokeWidth = 2f)

    var drawYAxis: AxisRenderer = YAxisRenderer(SolidColor(Color.Black), strokeWidth = 2f)

    var data: Data = Data(emptyList())

    val seriesRenderers: MutableMap<String, SeriesRenderer> = mutableMapOf()

    var renderer: SeriesRenderer = LineRenderer(SolidColor(Color.Black))

    override fun xAxis(axisRenderer: AxisRenderer) {
        drawXAxis = axisRenderer
    }

    override fun yAxis(axisRenderer: AxisRenderer) {
        drawYAxis = axisRenderer
    }

    override fun data(vararg series: Series) {
        data = Data(series.toList())
    }

    override fun renderer(renderer: SeriesRenderer) {
        this.renderer = renderer
    }

    override fun seriesRendererFor(vararg seriesName: String, renderer: SeriesRenderer) {
        seriesName.forEach {
            seriesRenderers[it] = renderer
        }
    }
}

@Preview(name = "Chart")
@Composable
fun PreviewCoordinatePlane() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Chart(
                modifier = Modifier.weight(1f),
                bounds = DataBounds(-1f, 5f, 0f, 9f)
            ) {
                data(
                    seriesOf(
                        "First",
                        pointOf(0f, 1f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    seriesOf(
                        "Second",
                        pointOf(0f, 0f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    seriesOf(
                        "Third",
                        pointOf(0f, 1f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    )
                )

                seriesRendererFor("First", renderer = PointRenderer(SolidColor(Color.Blue), 10f))
                seriesRendererFor(
                    "Second", renderer = LineRenderer(
                        SolidColor(Color.Green),
                        strokeWidth = 5f,
                    )
                )
                seriesRendererFor("Third", renderer = BarRenderer(SolidColor(Color.Red), 15f))
            }

            Text(modifier = Modifier.weight(1f), text = "Another fine chart")
        }
    }
}