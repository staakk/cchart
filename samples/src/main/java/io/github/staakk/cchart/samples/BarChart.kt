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
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.defaultHorizontalLabelRenderer
import io.github.staakk.cchart.label.defaultVerticalLabelRenderer
import io.github.staakk.cchart.renderer.bar.BarProcessor
import io.github.staakk.cchart.style.PrimitiveStyle
import io.github.staakk.cchart.verticalAxis

@Composable
fun BarChartScreen() {
    val labels = listOf(
        defaultHorizontalLabelRenderer(),
        defaultVerticalLabelRenderer(),
    )
    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 6f, 0f, 5f)
    ) {
        val styles = listOf(
            PrimitiveStyle(brush = SolidColor(Colors.Indigo)),
            PrimitiveStyle(brush = SolidColor(Colors.Green))
        )
        val barProcessor = BarProcessor(
            preferredWidth = 64f,
            style = { indexInGroup, _ -> styles[indexInGroup] },
        )
        series(
            seriesOf(
                    pointOf(1f, 1f),
                    pointOf(1f, 1.5f),
                    pointOf(2f, 1.5f),
                    pointOf(2f, 1f),
                    pointOf(3f, 4f),
                    pointOf(3f, 4.5f),
                    pointOf(4f, 3.5f),
                    pointOf(4f, 3.5f),
                    pointOf(5f, 2f),
                    pointOf(5f, 1f)
            ),
            barProcessor,
        )

        horizontalAxis()
        verticalAxis()
        labels.forEach { label(it) }
    }
}

@Preview
@Composable
fun PreviewBarChart() {
    Surface {
        BarChartScreen()
    }
}