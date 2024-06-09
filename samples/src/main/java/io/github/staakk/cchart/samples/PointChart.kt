package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation.Companion.Horizontal
import io.github.staakk.cchart.axis.AxisOrientation.Companion.Vertical
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.point.DrawPoints
import io.github.staakk.cchart.style.PrimitiveStyle
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PointChartScreen() {
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }

    val labels = arrayOf(
        horizontalLabels(),
        verticalLabels(),
    )

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 5.5f, 0f, 5.5f)
    ) {
        series(
            Series(SampleData.series.take(25).toList()),
            DrawPoints(
                pointSize,
                PrimitiveStyle(brush = SolidColor(Colors.Red)),
            )
        )

        features(
            Axis(Horizontal, 0.0f),
            Axis(Vertical, 0.0f),
            *labels,
        )
    }
}

@Preview
@Composable
fun PointLineChartScreen() {
    Surface {
        PointChartScreen()
    }
}