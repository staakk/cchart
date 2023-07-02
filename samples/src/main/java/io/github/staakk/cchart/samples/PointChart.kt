package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.pointRenderer
import io.github.staakk.cchart.verticalAxis

@Composable
fun PointChartScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 5.5f, 0f, 5.5f)
    ) {
        series(
            Series(SampleData.series.take(25).toList()),
            renderer = pointRenderer(
                size = pointSize,
                pointDrawer = circleDrawer { brush = SolidColor(Colors.Red) },
            )
        )

        horizontalAxis()
        verticalAxis()

        horizontalAxisLabels(horizontalLabelRenderer)
        verticalAxisLabels(verticalLabelRenderer)
    }
}

@Preview
@Composable
fun PointLineChartScreen() {
    Surface {
        PointChartScreen()
    }
}