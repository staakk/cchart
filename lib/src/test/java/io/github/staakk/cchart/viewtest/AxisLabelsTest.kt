package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.LabelOrientation
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.label.defaultHorizontalLabelRenderer
import io.github.staakk.cchart.label.defaultVerticalLabelRenderer
import io.github.staakk.cchart.label.labelRenderer
import io.github.staakk.cchart.verticalAxis
import org.junit.Rule
import org.junit.Test

class AxisLabelsTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun labels() {
        paparazzi.snapshot {
            val verticalLabels = defaultVerticalLabelRenderer()
            val rightSideVerticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                alignment = Alignment.CenterStart,
                labelOffset = Offset(12f, 0f)
            )
            val rightLocationVerticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                locationPercent = 1f
            )
            val rightSideRightLocationVerticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                locationPercent = 1f,
                alignment = Alignment.CenterStart,
                labelOffset = Offset(12f, 0f)
            )
            val horizontalLabels = defaultHorizontalLabelRenderer()
            val aboveHorizontalLabels = labelRenderer(
                orientation = LabelOrientation.Horizontal,
                alignment = Alignment.BottomCenter,
                labelOffset = Offset(0f, -12f)
            )
            val topHorizontalLabels = labelRenderer(
                orientation = LabelOrientation.Horizontal,
                locationPercent = 1f
            )
            val topAboveHorizontalLabels = labelRenderer(
                orientation = LabelOrientation.Horizontal,
                locationPercent = 1f,
                alignment = Alignment.BottomCenter,
                labelOffset = Offset(0f, -12f)
            )
            Chart(
                modifier = Modifier
                    .padding(32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                verticalAxis()
                verticalAxis(positionPercent = 1f)

                horizontalAxis()
                horizontalAxis(positionPercent = 1f)

                label(verticalLabels)
                label(rightSideVerticalLabels)
                label(rightLocationVerticalLabels)
                label(rightSideRightLocationVerticalLabels)
                label(horizontalLabels)
                label(aboveHorizontalLabels)
                label(topHorizontalLabels)
                label(topAboveHorizontalLabels)
            }
        }
    }

    @Test
    fun multiLineLabels() {
        paparazzi.snapshot {
            val verticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                labelsProvider = MultiLineLabelsProvider,
            )
            val rightSideVerticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                alignment = Alignment.CenterStart,
                labelOffset = Offset(12f, 0f),
                labelsProvider = MultiLineLabelsProvider
            )
            val rightLocationVerticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                locationPercent = 1f,
                labelsProvider = MultiLineLabelsProvider
            )
            val rightSideRightLocationVerticalLabels = labelRenderer(
                orientation = LabelOrientation.Vertical,
                locationPercent = 1f,
                alignment = Alignment.CenterStart,
                labelOffset = Offset(12f, 0f),
                labelsProvider = MultiLineLabelsProvider
            )
            val horizontalLabels = labelRenderer(
                orientation = LabelOrientation.Horizontal,
                labelsProvider = MultiLineLabelsProvider,
            )
            val aboveHorizontalLabels = labelRenderer(
                orientation = LabelOrientation.Horizontal,
                alignment = Alignment.BottomCenter,
                labelsProvider = MultiLineLabelsProvider,
                labelOffset = Offset(0f, -12f)
            )
            val topHorizontalLabels = labelRenderer(
                    orientation = LabelOrientation.Horizontal,
                    locationPercent = 1f,
                    labelsProvider = MultiLineLabelsProvider
                )
            val topAboveHorizontalLabels = labelRenderer(
                orientation = LabelOrientation.Horizontal,
                locationPercent = 0f,
                alignment = Alignment.BottomCenter,
                labelOffset = Offset(0f, -12f),
                labelsProvider = MultiLineLabelsProvider
            )
            Chart(
                modifier = Modifier
                    .padding(48.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                verticalAxis()
                verticalAxis(positionPercent = 1f)

                horizontalAxis()
                horizontalAxis(positionPercent = 1f)

                label(verticalLabels)
                label(rightSideVerticalLabels)
                label(rightLocationVerticalLabels)
                label(rightSideRightLocationVerticalLabels)
                label(horizontalLabels)
                label(aboveHorizontalLabels)
                label(topHorizontalLabels)
                label(topAboveHorizontalLabels)

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