package io.github.staakk.cchart.samples

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.LabelOrientation
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.label.defaultVerticalLabelRenderer
import io.github.staakk.cchart.label.labelRenderer
import io.github.staakk.cchart.renderer.bar.BarProcessor
import io.github.staakk.cchart.style.PrimitiveStyle
import io.github.staakk.cchart.verticalAxis
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateLabelsChartScreen() {
    val labels = listOf(
        labelRenderer(
            orientation = LabelOrientation.Horizontal,
            labelsProvider = DateLabelsProvider
        ),
        defaultVerticalLabelRenderer(),
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

        horizontalAxis()
        verticalAxis()

        labels.forEach { label(it) }
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