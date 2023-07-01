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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.axis.axisDrawer
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.style.lineStyle
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.LabelsProvider
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.*
import io.github.staakk.cchart.renderer.CompositeSeriesRenderer.Companion.combine
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
            val horizontalLabelRenderer = horizontalLabelRenderer()
            val verticalLabelRenderer = verticalLabelRenderer()
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
                    renderer = lineRenderer(lineDrawer { brush = SolidColor(Blue) })
                )

                verticalAxis(
                    verticalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(DarkGrey) }
                    )
                )

                horizontalAxis(
                    horizontalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(DarkGrey) }
                    )
                )

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)

                grid(
                    gridRenderer(
                        orientation = GridOrientation.HORIZONTAL,
                        lineStyle = lineStyle { brush = SolidColor(LightGrey) }
                    )
                )
            }
            //endtag=line_chart
        }
    }

    @Test
    fun barChart() {
        paparazzi.snapshot {
            //tag=bar_chart
            val horizontalLabelRenderer = horizontalLabelRenderer(
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
            val verticalLabelRenderer = verticalLabelRenderer { min, max ->
                (min.toInt()..max.toInt())
                    .filter { it % 25 == 0 }
                    .map { "$it%" to it.toFloat() }
            }
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
                    groupedSeriesOf(
                        listOf(
                            pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 78f),
                            pointOf(LocalDate.of(2020, 10, 1).toEpochDay(), 68f),
                        ),
                        listOf(
                            pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 56f),
                            pointOf(LocalDate.of(2020, 11, 1).toEpochDay(), 45f),
                        ),
                        listOf(
                            pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 82f),
                            pointOf(LocalDate.of(2020, 12, 1).toEpochDay(), 86f),
                        )
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 64f,
                        barDrawer = barDrawer { index, _ ->
                            SolidColor(
                                when (index) {
                                    0 -> DeepPurple
                                    1 -> Green
                                    else -> Pink
                                }
                            )
                        }
                    )
                )

                verticalAxis(
                    verticalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(DarkGrey) }
                    )
                )

                horizontalAxis(
                    horizontalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(DarkGrey) }
                    )
                )

                dataLabels {
                    Text(
                        modifier = Modifier.align(
                            HorizontalAlignment.CENTER,
                            VerticalAlignment.TOP
                        ),
                        text = "${data.y.toInt()}%",
                        style = TextStyle(fontSize = 12.sp)
                    )
                }

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)
            }
            //endtag=bar_chart
        }
    }

    @Test
    fun twoAxisChart() {
        paparazzi.snapshot {
            //tag=two_axis_chart
            val horizontalLabelRenderer = horizontalLabelRenderer()
            val density = LocalDensity.current
            val verticalLabelRenderer1 = verticalLabelRenderer(
                brush = SolidColor(Blue),
                textStyle = TextStyle(fontSize = 12.sp),
                location = 0f,
                alignment = Alignment.CenterEnd
            )
            val verticalLabelRenderer2 = verticalLabelRenderer(
                brush = SolidColor(Green),
                location = 1f,
                alignment = Alignment.CenterStart,
                labelOffset = Offset(12f, 0f)
            ) { min, max ->
                (min.toInt()..(max.toInt() + 1))
                    .map { "${it * 2}" to it.toFloat() }
            }
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp, end = 32.dp)
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
                    renderer = combine(
                        lineRenderer(lineDrawer { brush = SolidColor(Blue) }),
                        pointRenderer(
                            size = with(density) { 8.dp.toPx() }.let { Size(it, it) },
                            pointDrawer = circleDrawer { brush = SolidColor(LightBlue) }
                        )
                    )
                )

                series(
                    seriesOf(
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
                    ),
                    renderer = combine(
                        lineRenderer(lineDrawer { brush = SolidColor(Green) }),
                        pointRenderer(
                            size = with(density) { 8.dp.toPx() }.let { Size(it, it) },
                            pointDrawer = circleDrawer { brush = SolidColor(LightGreen) }
                        )
                    )
                )

                verticalAxis(
                    verticalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(Blue) },
                        location = 0f
                    )
                )

                verticalAxis(
                    verticalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(Green) },
                        location = 1f
                    )
                )

                horizontalAxis(
                    horizontalAxisRenderer(
                        axisDrawer = axisDrawer { brush = SolidColor(DarkGrey) }
                    )
                )

                verticalAxisLabels(verticalLabelRenderer1)

                verticalAxisLabels(verticalLabelRenderer2)

                horizontalAxisLabels(horizontalLabelRenderer)

                grid(
                    gridRenderer(
                        orientation = GridOrientation.HORIZONTAL,
                        lineStyle = lineStyle { brush = SolidColor(LightGrey) }
                    )
                )
            }
            //endtag=two_axis_chart
        }
    }
}