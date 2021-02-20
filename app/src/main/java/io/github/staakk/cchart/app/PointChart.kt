package io.github.staakk.cchart.app

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.renderer.PointRenderer

@Composable
fun PointChartScreen() {
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        bounds = DataBounds(0f, 10f, 0f, 5f)
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
            renderer = PointRenderer(brush = SolidColor(Color.Red), radius = 10f)
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