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
import androidx.compose.ui.graphics.PathEffect
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
import io.github.staakk.cchart.data.ChartData
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.DataPoint
import io.github.staakk.cchart.data.Series
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

    val dataBounds = scope.chartData.bounds

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
                    scope.chartData
                        .series
                        .forEach { series ->
                            scope.seriesRenderers[series.name]
                                ?.apply { this@drawScope.render(rendererContext, series) }
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

    fun items(items: ChartData)

    fun seriesRendererFor(seriesName: String, seriesRenderer: SeriesRenderer)
}

private class ChartScopeImpl : ChartScope {

    var drawXAxis: AxisRenderer = XAxisRenderer(SolidColor(Color.Black))

    var drawYAxis: AxisRenderer = YAxisRenderer(SolidColor(Color.Black))

    var chartData: ChartData = ChartData(emptyList())

    val seriesRenderers: MutableMap<String, SeriesRenderer> = mutableMapOf()

    override fun xAxis(axisRenderer: AxisRenderer) {
        drawXAxis = axisRenderer
    }

    override fun yAxis(axisRenderer: AxisRenderer) {
        drawYAxis = axisRenderer
    }

    override fun items(items: ChartData) {
        chartData = items
    }

    override fun seriesRendererFor(seriesName: String, seriesRenderer: SeriesRenderer) {
        seriesRenderers[seriesName] = seriesRenderer
    }
}

@Preview(name = "Chart")
@Composable
fun PreviewCoordinatePlane() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Chart(
                modifier = Modifier.weight(1f)
            ) {
                items(
                    ChartData(
                        series = listOf(
                            Series(
                                name = "First",
                                points = listOf(
                                    DataPoint(0, 1),
                                    DataPoint(2, 8),
                                    DataPoint(3, 3),
                                    DataPoint(4, 4),
                                ),
                            ),
                            Series(
                                name = "Second",
                                points = listOf(
                                    DataPoint(0, 0),
                                    DataPoint(2, 8),
                                    DataPoint(3, 3),
                                    DataPoint(4, 4),
                                ),
                            ),
                            Series(
                                name = "Third",
                                points = listOf(
                                    DataPoint(0, 1),
                                    DataPoint(2, 8),
                                    DataPoint(3, 3),
                                    DataPoint(4, 4),
                                ),
                            )
                        )
                    )
                )

                seriesRendererFor("First", PointRenderer(SolidColor(Color.Blue), 10f))
                seriesRendererFor(
                    "Second", LineRenderer(
                        SolidColor(Color.Green),
                        strokeWidth = 5f,
                        pathEffect = PathEffect.dashPathEffect(
                            FloatArray(2) { 20f })
                    )
                )
                seriesRendererFor("Third", BarRenderer(SolidColor(Color.Red), 15f))
            }

            Text(modifier = Modifier.weight(1f), text = "Another fine chart")
        }
    }
}