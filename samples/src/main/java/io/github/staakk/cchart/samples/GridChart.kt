package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.lineRenderer

@Composable
fun GridChartScreen() {
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        viewport = Viewport(0f, 10f, 0f, 5f)
    ) {
        series(
            seriesOf(
                "Data",
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
            renderer = lineRenderer(brush = SolidColor(Color.Blue))
        )

        horizontalAxis(horizontalAxisRenderer())

        horizontalLabel(horizontalLabelRenderer())

        verticalAxis(verticalAxisRenderer())

        verticalLabel(verticalLabelRenderer())

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