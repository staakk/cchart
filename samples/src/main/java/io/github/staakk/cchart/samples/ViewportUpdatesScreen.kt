package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.ChartState
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.renderer.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.renderer.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.renderer.axis.Axis
import io.github.staakk.cchart.renderer.axis.AxisOrientation
import io.github.staakk.cchart.renderer.point.DrawPoints
import io.github.staakk.cchart.style.PrimitiveStyle

@Composable
fun ViewportUpdatesScreen() {
    @OptIn(ExperimentalTextApi::class)
    val labels = arrayOf(
        horizontalLabels(),
        verticalLabels(),
    )
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }
    Column {
        val viewport = Viewport(0f, 5.5f, 0f, 5.5f)
        val chartState = remember { ChartState(viewport) }
        Chart(
            modifier = Modifier
                .padding(start = 32.dp, bottom = 32.dp)
                .aspectRatio(1f, false),
            chartState = chartState,
            maxViewport = Viewport(-10f, 20f, -5f, 10f),
            minViewportSize = Size(5.5f, 5.5f),
            maxViewportSize = Size(10f, 10f),
            enableZoom = true
        ) {
            series(
                Series(SampleData.series.take(25).toList()),
                DrawPoints(
                    pointSize,
                    PrimitiveStyle(brush = SolidColor(Colors.Indigo)),
                )
            )

            features(
                Axis(AxisOrientation.Horizontal, 0.0f),
                Axis(AxisOrientation.Vertical, 0.0f),
                *labels,
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "Viewport: ${getViewportText(chartState.viewport)}"
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "Zoom: ${getZoomText(viewport, chartState.viewport)}"
            )
            Text(text = "Pan: ${getPanText(viewport, chartState.viewport)}")
        }

    }
}

private fun getViewportText(viewport: Viewport) =
    "minX=${viewport.minX.format(2)}, " +
            "maxX=${viewport.maxX.format(2)}, " +
            "minY=${viewport.minY.format(2)}, " +
            "maxY=${viewport.maxY.format(2)}"

private fun getZoomText(reference: Viewport, current: Viewport) =
    "x=${(reference.width / current.width).format(2)}, " +
            "y=${(reference.height / current.height).format(2)}"

private fun getPanText(reference: Viewport, current: Viewport): String {
    val referenceCenterX = (reference.minX + reference.maxX) / 2
    val referenceCenterY = (reference.minY + reference.maxY) / 2
    val currentCenterX = (current.minX + current.maxX) / 2
    val currentCenterY = (current.minY + current.maxY) / 2
    return "x=${(currentCenterX - referenceCenterX).format(2)}, " +
            "y=${(currentCenterY - referenceCenterY).format(2)}"
}

private fun Float.format(digits: Int) = "%.${digits}f".format(this)

@Preview
@Composable
fun PreviewViewportUpdatesScreen() {
    Surface {
        ViewportUpdatesScreen()
    }
}