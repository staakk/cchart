package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.defaultHorizontalLabelRenderer
import io.github.staakk.cchart.label.defaultVerticalLabelRenderer
import io.github.staakk.cchart.renderer.line.DrawLine
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.verticalAxis

@Composable
fun GridChartScreen() {
    val labels = listOf(
        defaultHorizontalLabelRenderer(),
        defaultVerticalLabelRenderer(),
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

        horizontalAxis()
        verticalAxis()

        labels.forEach { label(it) }

        grid(gridRenderer(orientation = GridOrientation.HORIZONTAL))
        grid(gridRenderer(orientation = GridOrientation.VERTICAL))
    }
}

@Preview
@Composable
fun PreviewGridChartScreen() {
    Surface {
        GridChartScreen()
    }
}