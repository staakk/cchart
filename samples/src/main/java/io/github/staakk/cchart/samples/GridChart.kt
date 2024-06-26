package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.grid.Grid
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.line.DrawLine
import io.github.staakk.cchart.style.LineStyle
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GridChartScreen() {
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
            DrawLine(
                LineStyle(
                    brush = SolidColor(Colors.Blue),
                    strokeWidth = 5f,
                )
            )
        )

        features(
            Axis(AxisOrientation.Horizontal, 0.0f),
            Axis(AxisOrientation.Vertical, 0.0f),
            Grid(GridOrientation.Horizontal),
            Grid(GridOrientation.Vertical),
            *labels,
        )
    }
}

@Preview
@Composable
fun PreviewGridChartScreen() {
    Surface {
        GridChartScreen()
    }
}