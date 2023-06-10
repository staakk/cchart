package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.lineDrawer
import io.github.staakk.cchart.renderer.lineRenderer
import io.github.staakk.cchart.util.Alignment
import org.junit.Rule
import org.junit.Test

class AxisLabelsTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun labels() {
        paparazzi.snapshot {
            val verticalLabels = verticalLabelRenderer()
            val rightSideVerticalLabels = verticalLabelRenderer(
                alignment = Alignment.CenterRight,
                labelOffset = Offset(12f, 0f)
            )
            val rightLocationVerticalLabels =
                verticalLabelRenderer(location = 1f)
            val rightSideRightLocationVerticalLabels = verticalLabelRenderer(
                location = 1f,
                alignment = Alignment.CenterRight,
                labelOffset = Offset(12f, 0f)
            )
            val horizontalLabels = horizontalLabelRenderer()
            val aboveHorizontalLabels = horizontalLabelRenderer(
                alignment = Alignment.TopCenter,
                labelOffset = Offset(0f, -12f)
            )
            val topHorizontalLabels =
                horizontalLabelRenderer(location = 0f)
            val topAboveHorizontalLabels = horizontalLabelRenderer(
                location = 0f,
                alignment = Alignment.TopCenter,
                labelOffset = Offset(0f, -12f)
            )
            Chart(
                modifier = Modifier
                    .padding(32.dp)
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
                    renderer = lineRenderer(lineDrawer = lineDrawer(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = 1f))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = 0f))

                verticalAxisLabels(verticalLabels)

                verticalAxisLabels(rightSideVerticalLabels)

                verticalAxisLabels(rightLocationVerticalLabels)

                verticalAxisLabels(rightSideRightLocationVerticalLabels)

                horizontalAxisLabels(horizontalLabels)

                horizontalAxisLabels(aboveHorizontalLabels)

                horizontalAxisLabels(topHorizontalLabels)

                horizontalAxisLabels(topAboveHorizontalLabels)
            }
        }
    }

    @Test
    fun multiLineLabels() {
        paparazzi.snapshot {
            val verticalLabels = verticalLabelRenderer(labelsProvider = MultiLineLabelsProvider)
            val rightSideVerticalLabels = verticalLabelRenderer(
                alignment = Alignment.CenterRight,
                labelOffset = Offset(12f, 0f),
                labelsProvider = MultiLineLabelsProvider
            )
            val rightLocationVerticalLabels =
                verticalLabelRenderer(
                    location = 1f,
                    labelsProvider = MultiLineLabelsProvider
                )
            val rightSideRightLocationVerticalLabels = verticalLabelRenderer(
                location = 1f,
                alignment = Alignment.CenterRight,
                labelOffset = Offset(12f, 0f),
                labelsProvider = MultiLineLabelsProvider
            )
            val horizontalLabels = horizontalLabelRenderer(labelsProvider = MultiLineLabelsProvider)
            val aboveHorizontalLabels = horizontalLabelRenderer(
                alignment = Alignment.TopCenter,
                labelsProvider = MultiLineLabelsProvider,
                labelOffset = Offset(0f, -12f)
            )
            val topHorizontalLabels =
                horizontalLabelRenderer(
                    location = 0f,
                    labelsProvider = MultiLineLabelsProvider
                )
            val topAboveHorizontalLabels = horizontalLabelRenderer(
                location = 0f,
                alignment = Alignment.TopCenter,
                labelOffset = Offset(0f, -12f),
                labelsProvider = MultiLineLabelsProvider
            )
            Chart(
                modifier = Modifier
                    .padding(48.dp)
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
                    renderer = lineRenderer(lineDrawer = lineDrawer(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = 1f))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = 0f))

                verticalAxisLabels(verticalLabels)

                verticalAxisLabels(rightSideVerticalLabels)

                verticalAxisLabels(rightLocationVerticalLabels)

                verticalAxisLabels(rightSideRightLocationVerticalLabels)

                horizontalAxisLabels(horizontalLabels)

                horizontalAxisLabels(aboveHorizontalLabels)

                horizontalAxisLabels(topHorizontalLabels)

                horizontalAxisLabels(topAboveHorizontalLabels)

                grid(gridRenderer())
            }
        }
    }

    private object MultiLineLabelsProvider : LabelsProvider {
        override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
            return (min.toInt()..(max.toInt() + 1)).map { "$it\n$it$it" to it.toFloat() }
        }
    }
}