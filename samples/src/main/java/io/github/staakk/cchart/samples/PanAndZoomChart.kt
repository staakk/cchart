package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.LabelOrientation
import io.github.staakk.cchart.label.labelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.pointRenderer
import io.github.staakk.cchart.verticalAxis

@Composable
fun PanAndZoomScreen() {
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }

    val horizontalLabelRenderer = labelRenderer(
        orientation = LabelOrientation.Horizontal,
        locationPercent = 0f,
        clip = true,
    )
    val verticalLabelRenderer = labelRenderer(
        orientation = LabelOrientation.Vertical,
        alignment = Alignment.CenterEnd,
        labelOffset = with(LocalDensity.current) { Offset(-4.dp.toPx(), 0f) },
        locationPercent = 0f,
        clip = true,
    )

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 10f, 0f, 5f),
        maxViewport = Viewport(-10f, 20f, -5f, 20f),
        minViewportSize = Size(5f, 5f),
        maxViewportSize = Size(10f, 10f),
        enableZoom = true
    ) {
        series(
            data,
            renderer = pointRenderer(
                size = pointSize,
                pointDrawer = circleDrawer { brush = SolidColor(Colors.Red) }
            )
        )

        horizontalAxis()
        verticalAxis()

        label(horizontalLabelRenderer)
        label(verticalLabelRenderer)
    }
}

private val data = seriesOf(
    0f to 1f,
    2f to 1.5f,
    3f to 4f,
    4f to 3.5f,
    5f to 2f,
    6f to 1.3f,
    7f to 4f,
    8f to 4.5f,
    9f to 4.7f,
)

@Preview
@Composable
fun PreviewPanAndZoomScreen() {
    Surface {
        PanAndZoomScreen()
    }
}