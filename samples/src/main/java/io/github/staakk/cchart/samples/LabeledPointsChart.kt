package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.drawCircle
import io.github.staakk.cchart.renderer.pointRenderer


@Composable
fun LabeledPointsScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        viewport = Viewport(0f, 10f, 0f, 5f)
    ) {
        series(
            seriesOf(
                pointOf(0f, 1f),
                pointOf(3f, 4f),
                pointOf(5f, 2f),
                pointOf(7f, 4f),
                pointOf(9f, 4.7f),
            ),
            renderer = pointRenderer(
                radius = 10f,
                circleDrawer = drawCircle(brush = SolidColor(Colors.Red))
            )
        )

        horizontalAxis(horizontalAxisRenderer())

        horizontalAxisLabels(horizontalLabelRenderer)

        verticalAxis(verticalAxisRenderer())

        verticalAxisLabels(verticalLabelRenderer)

        dataLabels {
            Text(
                modifier = Modifier.padding(bottom = 4.dp)
                    .align(HorizontalAlignment.CENTER, VerticalAlignment.CENTER),
                text = "(${point.x}, ${point.y})"
            )
        }
    }
}

@Preview
@Composable
fun PreviewLabeledPointsScreen() {
    Surface {
        LabeledPointsScreen()
    }
}