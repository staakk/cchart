package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.LabelOrientation
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.labels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.renderer.axis.Axis
import io.github.staakk.cchart.renderer.axis.AxisOrientation
import io.github.staakk.cchart.renderer.grid.Grid
import io.github.staakk.cchart.renderer.grid.GridOrientation
import io.github.staakk.cchart.style.LabelStyle
import org.junit.Rule
import org.junit.Test

class LabelsTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun labelsOptions() {
        @OptIn(ExperimentalTextApi::class)
        paparazzi.snapshot {
            val verticalLabels = verticalLabels()
            val rightSideVerticalLabels = labels(
                orientation = LabelOrientation.Vertical,
                locationPercent = 0f,
                labelStyle = LabelStyle(
                    alignment = Alignment.CenterStart,
                    labelOffset = Offset(12f, 0f),
                ),
            )
            val rightLocationVerticalLabels = verticalLabels(
                locationPercent = 1f
            )
            val rightSideRightLocationVerticalLabels = labels(
                orientation = LabelOrientation.Vertical,
                locationPercent = 1f,
                labelStyle = LabelStyle(
                    alignment = Alignment.CenterStart,
                    labelOffset = Offset(12f, 0f),
                ),
            )
            val horizontalLabels = horizontalLabels()
            val aboveHorizontalLabels = labels(
                orientation = LabelOrientation.Horizontal,
                locationPercent = 0f,
                labelStyle = LabelStyle(
                    alignment = Alignment.BottomCenter,
                    labelOffset = Offset(0f, -12f),
                ),
            )
            val topHorizontalLabels = horizontalLabels(locationPercent = 1f)
            val topAboveHorizontalLabels = labels(
                orientation = LabelOrientation.Horizontal,
                locationPercent = 1f,
                labelStyle = LabelStyle(
                    alignment = Alignment.BottomCenter,
                    labelOffset = Offset(0f, -12f),
                ),
            )
            Chart(
                modifier = Modifier
                    .padding(32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                features(
                    Axis(AxisOrientation.Horizontal, 0.0f),
                    Axis(AxisOrientation.Horizontal, 1.0f),
                    Axis(AxisOrientation.Vertical, 0.0f),
                    Axis(AxisOrientation.Vertical, 1.0f),
                    verticalLabels,
                    rightSideVerticalLabels,
                    rightLocationVerticalLabels,
                    rightSideRightLocationVerticalLabels,
                    horizontalLabels,
                    aboveHorizontalLabels,
                    topHorizontalLabels,
                    topAboveHorizontalLabels,
                )
            }
        }
    }

    @Test
    fun multiLineLabels() {
        @OptIn(ExperimentalTextApi::class)
        paparazzi.snapshot {
            val verticalLabels = verticalLabels(
                labelsProvider = MultiLineLabelsProvider,
            )
            val rightSideVerticalLabels = verticalLabels(
                labelStyle = LabelStyle(
                    alignment = Alignment.CenterStart,
                    labelOffset = Offset(12f, 0f),
                ),
                labelsProvider = MultiLineLabelsProvider
            )
            val rightLocationVerticalLabels = verticalLabels(
                locationPercent = 1f,
                labelsProvider = MultiLineLabelsProvider
            )
            val rightSideRightLocationVerticalLabels = verticalLabels(
                locationPercent = 1f,
                labelStyle = LabelStyle(
                    alignment = Alignment.CenterStart,
                    labelOffset = Offset(12f, 0f),
                ),
                labelsProvider = MultiLineLabelsProvider
            )
            val horizontalLabels = horizontalLabels(labelsProvider = MultiLineLabelsProvider)
            val aboveHorizontalLabels = horizontalLabels(
                labelsProvider = MultiLineLabelsProvider,
                labelStyle = LabelStyle(
                    alignment = Alignment.BottomCenter,
                    labelOffset = Offset(0f, -12f),
                ),
            )
            val topHorizontalLabels = horizontalLabels(
                locationPercent = 1f,
                labelsProvider = MultiLineLabelsProvider
            )
            val topAboveHorizontalLabels = horizontalLabels(
                locationPercent = 0f,
                labelStyle = LabelStyle(
                    alignment = Alignment.BottomCenter,
                    labelOffset = Offset(0f, -12f),
                ),
                labelsProvider = MultiLineLabelsProvider
            )
            Chart(
                modifier = Modifier
                    .padding(48.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                features(
                    Axis(AxisOrientation.Horizontal, 0.0f),
                    Axis(AxisOrientation.Horizontal, 1.0f),
                    Axis(AxisOrientation.Vertical, 0.0f),
                    Axis(AxisOrientation.Vertical, 1.0f),
                    Grid(GridOrientation.Horizontal),
                    verticalLabels,
                    rightSideVerticalLabels,
                    rightLocationVerticalLabels,
                    rightSideRightLocationVerticalLabels,
                    horizontalLabels,
                    aboveHorizontalLabels,
                    topHorizontalLabels,
                    topAboveHorizontalLabels,
                )
            }
        }
    }

    private object MultiLineLabelsProvider : LabelsProvider {
        override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
            return (min.toInt()..(max.toInt() + 1)).map { "$it\n$it$it" to it.toFloat() }
        }
    }
}