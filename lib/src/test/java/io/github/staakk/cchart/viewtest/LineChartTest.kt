package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.line.DrawLine
import io.github.staakk.cchart.style.LineStyle
import org.junit.Rule
import org.junit.Test

class LineChartTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun lineChart() {
        paparazzi.snapshot {
            @OptIn(ExperimentalTextApi::class)
            val labels = arrayOf(
                horizontalLabels(),
                verticalLabels(),
            )
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
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
                    DrawLine(LineStyle(brush = SolidColor(Color.Blue)))
                )

                features(
                    Axis(AxisOrientation.Horizontal, 0.0f),
                    Axis(AxisOrientation.Vertical, 0.0f),
                    *labels,
                )
            }
        }
    }
}