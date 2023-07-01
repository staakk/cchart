package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
import org.junit.Rule
import org.junit.Test

class BarChartTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun barChart() {
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
                    groupedSeriesOf(
                        pointOf(1f, 1f),
                        pointOf(2f, 1.5f),
                        pointOf(3f, 4f),
                        pointOf(4f, 3.5f),
                        pointOf(5f, 2f),
                        pointOf(6f, 1.3f),
                        pointOf(7f, 4f),
                        pointOf(8f, 4.5f),
                        pointOf(9f, 4.7f),
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 50f,
                        barDrawer = barDrawer { _, _ -> SolidColor(Color.Blue) }
                    )
                )

                verticalAxis()

                horizontalAxis()

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)
            }
        }
    }

    @Test
    fun multiSeriesBarChart() {
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
                    groupedSeriesOf(
                        listOf(
                            pointOf(1f, 1f),
                            pointOf(1f, 1f),
                        ),
                        listOf(
                            pointOf(2f, 1.5f),
                            pointOf(2f, 1.5f),
                        ),
                        listOf(
                            pointOf(3f, 4f),
                            pointOf(3f, 4f),
                        ),
                        listOf(
                            pointOf(4f, 3.5f),
                            pointOf(4f, 3.5f),
                        ),
                        listOf(
                            pointOf(5f, 2f),
                            pointOf(5f, 2f),
                        ),
                        listOf(
                            pointOf(6f, 1.3f),
                            pointOf(6f, 1.3f),
                        ),
                        listOf(
                            pointOf(7f, 4f),
                            pointOf(7f, 4f),
                        ),
                        listOf(
                            pointOf(8f, 4.5f),
                            pointOf(8f, 4.5f),
                        ),
                        listOf(
                            pointOf(9f, 4.7f),
                            pointOf(9f, 4.7f),
                        )
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 50f,
                        barDrawer = barDrawer { index, _ ->
                            val color = when (index) {
                                0 -> Color.Blue
                                1 -> Color.Green
                                else -> Color.Red
                            }
                            SolidColor(color)
                        },
                    )
                )

                verticalAxis()

                horizontalAxis()

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)
            }
        }
    }

    @Test
    fun multiSeriesMinimalSpacingBarChart() {
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
                    groupedSeriesOf(
                        listOf(
                            pointOf(1f, 1f),
                            pointOf(1f, 1f),
                        ),
                        listOf(
                            pointOf(2f, 1.5f),
                            pointOf(2f, 1.5f),
                        ),
                        listOf(
                            pointOf(3f, 4f),
                            pointOf(3f, 4f),
                        ),
                        listOf(
                            pointOf(4f, 3.5f),
                            pointOf(4f, 3.5f),
                        ),
                        listOf(
                            pointOf(5f, 2f),
                            pointOf(5f, 2f),
                        ),
                        listOf(
                            pointOf(6f, 1.3f),
                            pointOf(6f, 1.3f),
                        ),
                        listOf(
                            pointOf(7f, 4f),
                            pointOf(7f, 4f),
                        ),
                        listOf(
                            pointOf(8f, 4.5f),
                            pointOf(8f, 4.5f),
                        ),
                        listOf(
                            pointOf(9f, 4.7f),
                            pointOf(9f, 4.7f),
                        )
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 50f,
                        minimalSpacing = 50f,
                        barDrawer = barDrawer { index, _ ->
                            val color = when (index) {
                                0 -> Color.Blue
                                1 -> Color.Green
                                else -> Color.Red
                            }
                            SolidColor(color)
                        }
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