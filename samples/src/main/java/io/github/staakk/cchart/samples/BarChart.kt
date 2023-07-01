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
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.barDrawer
import io.github.staakk.cchart.renderer.barGroupRenderer
import io.github.staakk.cchart.verticalAxis

@Composable
fun BarChartScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()
    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 6f, 0f, 5f)
    ) {
        series(
            groupedSeriesOf(
                listOf(
                    pointOf(1f, 1f),
                    pointOf(1f, 1.5f),
                ),
                listOf(
                    pointOf(2f, 1.5f),
                    pointOf(2f, 1f),
                ),
                listOf(
                    pointOf(3f, 4f),
                    pointOf(3f, 4.5f),
                ),
                listOf(
                    pointOf(4f, 3.5f),
                    pointOf(4f, 3.5f),
                ),
                listOf(
                    pointOf(5f, 2f),
                    pointOf(5f, 1f)
                )
            ),
            renderer = barGroupRenderer(
                preferredWidth = 64f,
                barDrawer = barDrawer { index, _ ->
                    SolidColor(
                        when (index) {
                            0 -> Colors.Indigo
                            1 -> Colors.Green
                            else -> Colors.Pink
                        }
                    )
                }
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
fun PreviewBarChart() {
    Surface {
        BarChartScreen()
    }
}