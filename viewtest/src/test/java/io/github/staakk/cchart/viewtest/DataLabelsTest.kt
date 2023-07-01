package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.pointRenderer
import io.github.staakk.cchart.verticalAxis
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DataLabelsTest {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun parameters() = HorizontalAlignment.values().flatMap { horizontalAlignment ->
            VerticalAlignment.values().map { verticalAlignment -> horizontalAlignment to verticalAlignment }
        }
    }

    @Parameterized.Parameter
    lateinit var alignments: Pair<HorizontalAlignment, VerticalAlignment>

    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun dataLabels() {
        paparazzi.snapshot {
            val horizontalLabelRenderer = horizontalLabelRenderer()
            val verticalLabelRenderer = verticalLabelRenderer()
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        pointOf(2f, 1.5f),
                        pointOf(4f, 3.5f),
                        pointOf(6f, 1.3f),
                        pointOf(8f, 4.5f),
                    ),
                    renderer = pointRenderer(pointDrawer = circleDrawer { brush = SolidColor(Color.Blue) })
                )

                verticalAxis()

                horizontalAxis()

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)

                dataLabels {
                    Text(
                        modifier = Modifier.align(alignments.first, alignments.second),
                        text = "(${data.x}, ${data.y})"
                    )
                }
            }
        }
    }
}