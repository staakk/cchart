package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.bar.BarProcessor
import io.github.staakk.cchart.style.PrimitiveStyle
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateLabelsChartScreen() {
    val labels = arrayOf(
        horizontalLabels(labelsProvider = DateLabelsProvider),
        verticalLabels(),
    )

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 48.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(
            minX = LocalDate.of(2020, 9, 1).toEpochDay(),
            maxX = LocalDate.of(2021, 1, 1).toEpochDay(),
            minY = 0f,
            maxY = 20f
        )
    ) {

        val styles = listOf(
            PrimitiveStyle(brush = SolidColor(Colors.DeepPurple)),
            PrimitiveStyle(brush = SolidColor(Colors.Green))
        )
        val barProcessor = BarProcessor(
            preferredWidth = 64f,
            style = { index, _ -> styles[index] },
        )
        series(
            seriesOf(
                pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 6f),
                pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 6.9f),
                pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 12f),
                pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 14f),
                pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 15f),
                pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 13.8f),
            ),
            barProcessor,
        )

        features(
            Axis(AxisOrientation.Horizontal, 0.0f),
            Axis(AxisOrientation.Vertical, 0.0f),
            *labels,
        )
    }
}

object DateLabelsProvider : LabelsProvider {
    private const val pattern = "MMMM \nyyyy"
    private val formatter = DateTimeFormatter.ofPattern(pattern)

    override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
        var startDate = LocalDate.of(2020, 10, 1)
        val endDate = LocalDate.of(2020, 12, 1)

        val labels = mutableListOf<Pair<String, Float>>()
        while (startDate.isBefore(endDate.plusDays(1))) {
            labels.add(startDate.format(formatter) to startDate.toEpochDay().toFloat())
            startDate = startDate.plusMonths(1)
        }
        return labels
    }
}

@Preview
@Composable
fun PreviewDateLabelsChart() {
    Surface {
        DateLabelsChartScreen()
    }
}