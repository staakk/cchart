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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.defaultHorizontalLabelRenderer
import io.github.staakk.cchart.label.defaultVerticalLabelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.pointRenderer
import io.github.staakk.cchart.verticalAxis

@Composable
fun PopupChartScreen() {
    val labels = listOf(
        defaultHorizontalLabelRenderer(),
        defaultVerticalLabelRenderer(),
    )
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }
    val popupPosition = remember { mutableStateOf<Pair<Data<*>, Offset>?>(null)}
    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 5.5f, 0f, 5.5f),
        onClick = { offset, point ->
            popupPosition.value = point to offset
        }
    ) {
        series(
            Series(SampleData.series.take(25).toList()),
            renderer = pointRenderer(
                size = pointSize,
                pointDrawer = circleDrawer { brush = SolidColor(Colors.Red) }
            )
        )

        horizontalAxis()
        verticalAxis()

        labels.forEach { label(it) }

        popupPosition.value?.let { (point, _) ->
            anchor(point) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
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
        PopupChartScreen()
    }
}