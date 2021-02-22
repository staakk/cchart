package io.github.staakk.cchart.app

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.NullAxisRenderer
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.label.NullLabelRenderer
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.renderer.barRenderer
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateLabelsChart() {
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        bounds = DataBounds(
            minX = LocalDate.of(2020, 9, 1).toEpochDay(),
            maxX = LocalDate.of(2021, 1, 1).toEpochDay(),
            minY = 0f,
            maxY = 20f
        )
    ) {
        series(
            seriesOf(
                "Data I",
                pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 6f),
                pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 12f),
                pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 15f),
            ),
            seriesOf(
                "Data II",
                pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 6.9f),
                pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 14f),
                pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 13.8f),
            ),
            renderer = barRenderer(
                brushProvider = {
                    SolidColor(
                        when (it) {
                            "Data I" -> Color.DeepPurple
                            "Data II" -> Color.Green
                            else -> Color.Pink
                        }
                    )
                },
                preferredWidth = 64f,
            )
        )

        topAxis(NullAxisRenderer, NullLabelRenderer)
        rightAxis(NullAxisRenderer, NullLabelRenderer)

        bottomAxis(
            axisRenderer = horizontalAxisRenderer(),
            labelRenderer = horizontalLabelRenderer(labelsProvider = DateLabelsProvider)
        )
    }
}

object DateLabelsProvider : LabelsProvider {
    private const val pattern = "MMM yy"
    private val formatter = DateTimeFormatter.ofPattern(pattern)

    override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
        var currentDate = LocalDate.ofEpochDay(min.toLong()).withDayOfMonth(1)
        val endDate = LocalDate.ofEpochDay(max.toLong()).withDayOfMonth(1)

        val labels = mutableListOf<Pair<String, Float>>()
        while (currentDate.isBefore(endDate)) {
            labels.add(currentDate.format(formatter) to currentDate.toEpochDay().toFloat())
            currentDate = currentDate.plusMonths(1)
        }
        return labels
    }

    override fun getMaxLength(): Int = pattern.length

    override fun getMaxLines(): Int = 1
}

@Preview
@Composable
fun PreviewDateLabelsChart() {
    Surface {
        DateLabelsChart()
    }
}