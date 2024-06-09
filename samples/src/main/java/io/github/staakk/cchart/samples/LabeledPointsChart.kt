package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.point.DrawPoints
import io.github.staakk.cchart.point.PointBoundsProvider
import io.github.staakk.cchart.style.PrimitiveStyle
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun LabeledPointsScreen() {
    val labels = arrayOf(
        Labels.horizontalLabels(),
        Labels.verticalLabels(),
    )
    val pointSize = with(LocalDensity.current) { Size(8.dp.toPx(), 8.dp.toPx()) }

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 5.5f, 0f, 5.5f)
    ) {
        series(
            Series(
                SampleData
                    .series
                    .drop(5)
                    .filterIndexed { index, _ -> index % 5 == 0 }
                    .take(4)
                    .toList(),
            ),
            DrawPoints(
                pointSize,
                PrimitiveStyle(brush = SolidColor(Colors.Red)),
            ),
            PointBoundsProvider(Size(1f, 1f))
        )

        features(
            Axis(AxisOrientation.Horizontal, 0.0f),
            Axis(AxisOrientation.Vertical, 0.0f),
            *labels,
        )

        dataLabels {
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .align(Alignment.Center),
                text = "(${String.format("%.2f", point.x)}, ${String.format("%.2f", point.y)})"
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