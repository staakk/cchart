package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.pointRenderer
import io.github.staakk.cchart.verticalAxis


@Composable
fun PanAndZoomScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 10f, 0f, 5f),
        maxViewport = Viewport(-10f, 20f, -5f, 10f),
        minViewportSize = Size(5f, 5f),
        maxViewportSize = Size(10f, 10f),
        enableZoom = true
    ) {
        series(
            seriesOf(
                pointOf(0f, 1f),
                pointOf(2f, 1.5f),
                pointOf(3f, 4f),
                pointOf(4f, 3.5f),
                pointOf(5f, 2f),
                pointOf(6f, 1.3f),
                pointOf(7f, 4f),
                pointOf(8f, 4.5f),
                pointOf(9f, 4.7f),
            ),
            renderer = pointRenderer(
                size = Size(20f, 20f),
                pointDrawer = circleDrawer { brush = SolidColor(Colors.Red) }
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
fun PreviewPanAndZoomScreen() {
    Surface {
        PanAndZoomScreen()
    }
}