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
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.combine
import io.github.staakk.cchart.renderer.lineRenderer
import io.github.staakk.cchart.renderer.pointRenderer

@Composable
fun CombinedChartScreen() {
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
            renderer = combine(
                lineRenderer(brush = SolidColor(Color.Pink)),
                pointRenderer(brush = SolidColor(Color.Indigo), radius = 10f)
            )
        )

        horizontalAxis(horizontalAxisRenderer())

        horizontalLabel(horizontalLabelRenderer())

        verticalAxis(verticalAxisRenderer())

        verticalLabel(verticalLabelRenderer())
    }
}

@Preview
@Composable
fun PreviewCombinedChartScreen() {
    Surface {
        CombinedChartScreen()
    }
}