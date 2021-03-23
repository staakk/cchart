package io.github.staakk.cchart.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.drawCircle
import io.github.staakk.cchart.renderer.pointRenderer

@Composable
fun PopupChartScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()
    val popupPosition = remember { mutableStateOf<Pair<Point, Offset>?>(null)}
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        viewport = Viewport(0f, 10f, 0f, 5f),
        onClick = { offset, point ->
            popupPosition.value = point to offset
        }
    ) {
        series(
            seriesOf(
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
            renderer = pointRenderer(
                size = Size(20f, 20f),
                pointDrawer = drawCircle(brush = SolidColor(Colors.Red))
            )
        )

        horizontalAxis(horizontalAxisRenderer())

        horizontalAxisLabels(horizontalLabelRenderer)

        verticalAxis(verticalAxisRenderer())

        verticalAxisLabels(verticalLabelRenderer)

        popupPosition.value?.let { (point, _) ->
            anchor(point) {
                Text(
                    modifier = Modifier
                        .align(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
                        .background(
                            brush = SolidColor(Colors.LightGreen),
                            shape = RoundedCornerShape(4.dp),
                            alpha = 0.5f
                        )
                        .clickable { popupPosition.value = null }
                        .padding(4.dp),
                    text = "Click to close",
                )
            }
        }
    }
}

@Preview
@Composable
fun PopupLineChartScreen() {
    Surface {
        PointChartScreen()
    }
}