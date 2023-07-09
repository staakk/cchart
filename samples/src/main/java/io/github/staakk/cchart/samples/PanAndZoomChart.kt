package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.renderer.axis.Axis
import io.github.staakk.cchart.renderer.axis.AxisOrientation
import io.github.staakk.cchart.renderer.point.DrawPoints
import io.github.staakk.cchart.style.LabelStyle
import io.github.staakk.cchart.style.PrimitiveStyle

@Composable
fun PanAndZoomScreen() {
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }
    @OptIn(ExperimentalTextApi::class)
    val labels = arrayOf(
        horizontalLabels(labelStyle = LabelStyle(clip = true)),
        verticalLabels(labelStyle = LabelStyle(clip = true)),
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
            DrawPoints(
                pointSize,
                PrimitiveStyle(brush = SolidColor(Colors.Red)),
            )
        )

        features(
            Axis(AxisOrientation.Horizontal, 0.0f),
            Axis(AxisOrientation.Vertical, 0.0f),
            *labels,
        )
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