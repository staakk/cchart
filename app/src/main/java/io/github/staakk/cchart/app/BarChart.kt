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
import io.github.staakk.cchart.renderer.barRenderer

@Composable
fun BarChart() {
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        bounds = DataBounds(0f, 6f, 0f, 5f),
        zoomRange = 0.5f..1.5f
    ) {
        series(
            seriesOf(
                "Data I",
                pointOf(1f, 1f),
                pointOf(2f, 1.5f),
                pointOf(3f, 4f),
                pointOf(4f, 3.5f),
                pointOf(5f, 2f)
            ),
            seriesOf(
                "Data II",
                pointOf(1f, 1.5f),
                pointOf(2f, 1f),
                pointOf(3f, 4.5f),
                pointOf(4f, 3.5f),
                pointOf(5f, 1f)
            ),
            renderer = barRenderer(
                brushProvider = {
                    SolidColor(
                        when (it) {
                            "Data I" -> Color.DeepPurple
                            "Data II" -> Color.Green
                            else -> Color.Pink
                        }
                    )
                },
                preferredWidth = 64f,
            )
        )
    }
}

@Preview
@Composable
fun PreviewBarChart() {
    Surface {
        BarChart()
    }
}