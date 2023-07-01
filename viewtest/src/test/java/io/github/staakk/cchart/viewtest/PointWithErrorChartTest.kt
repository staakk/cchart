package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.circleWithError
import io.github.staakk.cchart.renderer.pointRenderer
import io.github.staakk.cchart.verticalAxis
import org.junit.Rule
import org.junit.Test

class PointWithErrorChartTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun pointWithError() {
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
                        pointOf(2f, 1.5f, 0.5f, 0.3f),
                        pointOf(4f, 3.5f, 0.5f, 0.3f),
                        pointOf(6f, 1.3f, 0.5f, 0.3f),
                        pointOf(8f, 4.5f, 0.5f, 0.3f),
                    ),
                    renderer = pointRenderer(
                        pointDrawer = circleWithError(
                            circleDrawer = circleDrawer { brush = SolidColor(Color.Blue) }
                        )
                    )
                )

                verticalAxis()

                horizontalAxis()

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)
            }
        }
    }
}