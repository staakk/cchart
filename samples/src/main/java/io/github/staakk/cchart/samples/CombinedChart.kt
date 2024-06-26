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
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.line.DrawLine
import io.github.staakk.cchart.point.DrawPoints
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.style.PrimitiveStyle
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CombinedChartScreen() {
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }
    val labels = arrayOf(
        Labels.horizontalLabels(),
        Labels.verticalLabels(),
    )

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 5.5f, 0f, 5.5f)
    ) {
        val data = SampleData.series.take(25).toList()

        series(
            Series(data),
            DrawLine(
                LineStyle(
                    brush = SolidColor(Colors.Pink),
                    strokeWidth = 5f,
                )
            ),
        )

        series(
            Series(data),
            DrawPoints(
                pointSize,
                PrimitiveStyle(brush = SolidColor(Colors.Indigo))
            )
        )

        features(
            Axis(AxisOrientation.Horizontal, 0.0f),
            Axis(AxisOrientation.Vertical, 0.0f),
            *labels,
        )
    }
}

@Preview
@Composable
fun PreviewCombinedChartScreen() {
    Surface {
        CombinedChartScreen()
    }
}