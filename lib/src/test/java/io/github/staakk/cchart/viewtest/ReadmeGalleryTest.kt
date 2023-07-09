@file:Suppress("unused")

package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.renderer.label.LabelOrientation
import io.github.staakk.cchart.renderer.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.renderer.label.Labels.Companion.labels
import io.github.staakk.cchart.renderer.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.renderer.label.LabelsProvider
import io.github.staakk.cchart.renderer.*
import io.github.staakk.cchart.renderer.axis.Axis
import io.github.staakk.cchart.renderer.axis.AxisOrientation
import io.github.staakk.cchart.renderer.bar.BarProcessor
import io.github.staakk.cchart.renderer.grid.Grid
import io.github.staakk.cchart.renderer.grid.GridOrientation
import io.github.staakk.cchart.renderer.line.DrawLine
import io.github.staakk.cchart.renderer.point.DrawPoints
import io.github.staakk.cchart.style.LabelStyle
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.style.PrimitiveStyle
import io.github.staakk.cchart.style.lineStyle
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val Red = Color(0xFFE57474)
private val Pink = Color(0xFFF06292)
private val Purple = Color(0xFFBA68C8)
private val DeepPurple = Color(0xFF9575CD)
private val Indigo = Color(0xFF7986CB)
private val Blue = Color(0xFF64B5F6)
private val LightBlue = Color(0xFF4FC3F7)
private val Cyan = Color(0xFF4DD0E1)
private val Teal = Color(0xFF4DB6AC)
private val Green = Color(0xFF81C784)
private val LightGreen = Color(0xFFAED581)
private val Lime = Color(0xFFDCE775)
private val DarkGrey = Color(0xFF212121)
private val LightGrey = Color(0xFF424242)

class ReadmeGalleryTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun lineChart() {
        paparazzi.snapshot {
            //tag=line_chart
            @OptIn(ExperimentalTextApi::class)
            val labels = arrayOf(
                horizontalLabels(),
                verticalLabels(),
            )
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false)
                    .padding(bottom = 16.dp),
                viewport = Viewport(0f, 10f, 0f, 10f)
            ) {
                series(
                    seriesOf(
                        pointOf(0f, 1.3f),
                        pointOf(1f, 2.4f),
                        pointOf(2f, 2.3f),
                        pointOf(3f, 4.8f),
                        pointOf(4f, 4.3f),
                        pointOf(5f, 5.3f),
                        pointOf(6f, 5.7f),
                        pointOf(7f, 6.3f),
                        pointOf(8f, 6.1f),
                        pointOf(9f, 8.3f),
                        pointOf(10f, 9.1f),
                    ),
                    DrawLine(LineStyle(brush = SolidColor(Blue), strokeWidth = 5f))
                )

                val lineStyle = lineStyle { brush = SolidColor(DarkGrey) }
                features(
                    Axis(AxisOrientation.Horizontal, 0.0f, lineStyle),
                    Axis(AxisOrientation.Vertical, 0.0f, lineStyle),
                    Grid(
                        orientation = GridOrientation.Horizontal,
                        lineStyle = lineStyle { brush = SolidColor(LightGrey) }
                    ),
                    *labels,
                )
            }
            //endtag=line_chart
        }
    }

    @Test
    fun barChart() {
        @OptIn(ExperimentalTextApi::class)
        paparazzi.snapshot {
            //tag=bar_chart
            val horizontalLabelRenderer = labels(
                orientation = LabelOrientation.Horizontal,
                locationPercent = 0f,
                labelsProvider = object : LabelsProvider {
                    private val pattern = "MMMM \nyyyy"
                    private val formatter = DateTimeFormatter.ofPattern(pattern)

                    override fun provide(
                        min: Float,
                        max: Float
                    ): List<Pair<String, Float>> {
                        var currentDate = LocalDate.ofEpochDay(min.toLong()).withDayOfMonth(1)
                        val endDate = LocalDate.ofEpochDay(max.toLong()).withDayOfMonth(1)

                        val labels = mutableListOf<Pair<String, Float>>()
                        while (currentDate.isBefore(endDate)) {
                            labels.add(
                                currentDate.format(formatter) to currentDate.toEpochDay()
                                    .toFloat()
                            )
                            currentDate = currentDate.plusMonths(1)
                        }
                        return labels
                    }
                }
            )
            val verticalLabelRenderer = labels(
                orientation = LabelOrientation.Vertical,
                locationPercent = 0f,
                labelsProvider = { min, max ->
                    (min.toInt()..max.toInt())
                        .filter { it % 25 == 0 }
                        .map { "$it%" to it.toFloat() }
                }
            )
            val styles = listOf(
                PrimitiveStyle(brush = SolidColor(DeepPurple)),
                PrimitiveStyle(brush = SolidColor(Green))
            )
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false)
                    .padding(bottom = 16.dp),
                viewport = Viewport(
                    minX = LocalDate.of(2020, 9, 1).toEpochDay(),
                    maxX = LocalDate.of(2021, 1, 1).toEpochDay(),
                    minY = 0f,
                    maxY = 100f
                )
            ) {
                series(
                    seriesOf(
                        pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 78f),
                        pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 68f),
                        pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 56f),
                        pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 45f),
                        pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 82f),
                        pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 86f),
                    ),
                    BarProcessor(
                        preferredWidth = 64f,
                        style = { index, _ -> styles[index] }
                    )
                )

                val lineStyle = lineStyle { brush = SolidColor(DarkGrey) }
                features(
                    Axis(AxisOrientation.Horizontal, 0.0f, lineStyle),
                    Axis(AxisOrientation.Vertical, 0.0f, lineStyle),
                    verticalLabelRenderer,
                    horizontalLabelRenderer,
                )

                dataLabels {
                    Text(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        text = "${data.y.toInt()}%",
                        style = TextStyle(fontSize = 12.sp)
                    )
                }
            }
            //endtag=bar_chart
        }
    }

    @Test
    fun twoAxisChart() {
        @OptIn(ExperimentalTextApi::class)
        paparazzi.snapshot {
            //tag=two_axis_chart
            val horizontalLabel = horizontalLabels()
            val density = LocalDensity.current
            val verticalLabel1 = labels(
                orientation = LabelOrientation.Vertical,
                locationPercent = 0f,
                labelStyle = LabelStyle(
                    brush = SolidColor(Blue),
                    textStyle = TextStyle(fontSize = 12.sp),
                    alignment = Alignment.CenterEnd,
                )
            )
            val verticalLabel2 = labels(
                orientation = LabelOrientation.Vertical,
                locationPercent = 1f,
                labelStyle = LabelStyle(
                    brush = SolidColor(Green),
                    alignment = Alignment.CenterStart,
                    labelOffset = Offset(12f, 0f),
                ),
                labelsProvider = { min, max ->
                    (min.toInt()..(max.toInt() + 1))
                        .map { "${it * 2}" to it.toFloat() }
                }
            )
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp, end = 32.dp)
                    .aspectRatio(1f, false)
                    .padding(bottom = 16.dp),
                viewport = Viewport(0f, 10f, 0f, 10f)
            ) {
                val data1 = listOf(
                    pointOf(0f, 1.3f),
                    pointOf(1f, 2.4f),
                    pointOf(2f, 2.3f),
                    pointOf(3f, 4.8f),
                    pointOf(4f, 4.3f),
                    pointOf(5f, 5.3f),
                    pointOf(6f, 5.7f),
                    pointOf(7f, 6.3f),
                    pointOf(8f, 6.1f),
                    pointOf(9f, 8.3f),
                    pointOf(10f, 9.1f),
                )
                series(
                    Series(data1),
                    DrawLine(LineStyle(brush = SolidColor(Blue))),
                )
                series(
                    Series(data1),
                    DrawPoints(
                        pointSize = with(density) { 8.dp.toPx() }.let { Size(it, it) },
                        PrimitiveStyle(brush = SolidColor(LightBlue))
                    )
                )

                val data2 = listOf(
                    pointOf(0f, 9.1f),
                    pointOf(1f, 8.3f),
                    pointOf(2f, 6.1f),
                    pointOf(3f, 6.3f),
                    pointOf(4f, 5.3f),
                    pointOf(5f, 4.3f),
                    pointOf(6f, 4.8f),
                    pointOf(7f, 5.7f),
                    pointOf(8f, 2.3f),
                    pointOf(9f, 2.4f),
                    pointOf(10f, 1.3f),
                )
                series(
                    Series(data2),
                    DrawLine(LineStyle(brush = SolidColor(Green)))
                )

                series(
                    Series(data2),
                    DrawPoints(
                        with(density) { 8.dp.toPx() }.let { Size(it, it) },
                        PrimitiveStyle(brush = SolidColor(LightGreen))
                    )
                )

                features(
                    Axis(AxisOrientation.Vertical, 0.0f, lineStyle { brush = SolidColor(Blue) }),
                    Axis(AxisOrientation.Vertical, 1.0f, lineStyle { brush = SolidColor(Green) }),
                    Axis(
                        AxisOrientation.Horizontal,
                        0.0f,
                        lineStyle { brush = SolidColor(DarkGrey) }
                    ),
                    Grid(
                        orientation = GridOrientation.Horizontal,
                        lineStyle = lineStyle { brush = SolidColor(LightGrey) }
                    ),
                    verticalLabel1,
                    verticalLabel2,
                    horizontalLabel,
                )


            }
            //endtag=two_axis_chart
        }
    }
}