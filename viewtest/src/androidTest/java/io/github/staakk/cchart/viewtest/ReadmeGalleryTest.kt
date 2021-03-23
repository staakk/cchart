package io.github.staakk.cchart.viewtest

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.axis.VerticalAxisLocation
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.*
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

class ReadmeGalleryTest : ScreenshotTest {

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun lineChart() {
        composeRule.setContent {
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
                    renderer = lineRenderer(lineDrawer = drawLine(brush = SolidColor(Blue)))
                )

                verticalAxis(
                    verticalAxisRenderer(
                        brush = SolidColor(DarkGrey)
                    )
                )

                horizontalAxis(
                    horizontalAxisRenderer(
                        brush = SolidColor(DarkGrey)
                    )
                )

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)

                grid(
                    gridRenderer(
                        brush = SolidColor(LightGrey),
                        orientation = GridOrientation.HORIZONTAL
                    )
                )
            }
            //endtag=line_chart
        }

        compareScreenshot(composeRule)
    }

    @Test
    fun barChart() {
        composeRule.setContent {
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

                    override fun getMaxLength(): Int = pattern.length

                    override fun getMaxLines(): Int = 2
                }
            )
            val verticalLabelRenderer = verticalLabelRenderer(
                labelsProvider = object : LabelsProvider {
                    override fun provide(
                        min: Float,
                        max: Float
                    ): List<Pair<String, Float>> =
                        (min.toInt()..max.toInt())
                            .filter { it % 25 == 0 }
                            .map { "$it%" to it.toFloat() }

                    override fun getMaxLength(): Int = 3

                    override fun getMaxLines(): Int = 1

                }
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
                        barDrawer = drawBar { index, _ ->
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
                        brush = SolidColor(DarkGrey)
                    )
                )

                horizontalAxis(
                    horizontalAxisRenderer(
                        brush = SolidColor(DarkGrey)
                    )
                )

                dataLabels {
                    Text(
                        modifier = Modifier.align(
                            HorizontalAlignment.CENTER,
                            VerticalAlignment.TOP
                        ),
                        text = "${point.y.toInt()}%",
                        style = TextStyle(fontSize = 12.sp)
                    )
                }

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)
            }
            //endtag=bar_chart
        }

        compareScreenshot(composeRule)
    }

    @Test
    fun twoAxisChart() {
        composeRule.setContent {
            //tag=two_axis_chart
            val horizontalLabelRenderer = horizontalLabelRenderer()
            val density = LocalDensity.current
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
                        lineRenderer(lineDrawer = drawLine(brush = SolidColor(Blue))),
                        pointRenderer(
                            size = with(density) { 8.dp.toPx() }.let { Size(it, it) },
                            pointDrawer = drawCircle(brush = SolidColor(LightBlue))
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
                        lineRenderer(lineDrawer = drawLine(brush = SolidColor(Green))),
                        pointRenderer(
                            size = with(density) { 8.dp.toPx() }.let { Size(it, it) },
                            pointDrawer = drawCircle(brush = SolidColor(LightGreen))
                        )
                    )
                )

                verticalAxis(
                    verticalAxisRenderer(
                        brush = SolidColor(Blue),
                        location = VerticalAxisLocation.LEFT
                    )
                )

                verticalAxis(
                    verticalAxisRenderer(
                        brush = SolidColor(Green),
                        location = VerticalAxisLocation.RIGHT
                    )
                )

                horizontalAxis(
                    horizontalAxisRenderer(
                        brush = SolidColor(DarkGrey)
                    )
                )

                verticalAxisLabels(
                    verticalLabelRenderer(
                        paint = Paint().apply {
                            color = Blue.toArgb()
                            typeface = Typeface.DEFAULT
                            textSize = with(density) { 12.sp.toPx() }
                            isAntiAlias = true
                        },
                        location = VerticalLabelLocation.LEFT,
                        side = VerticalLabelSide.LEFT
                    )
                )

                verticalAxisLabels(verticalLabelRenderer(
                    paint = Paint().apply {
                        color = Green.toArgb()
                        typeface = Typeface.DEFAULT
                        textSize = with(density) { 12.sp.toPx() }
                        isAntiAlias = true
                    },
                    location = VerticalLabelLocation.RIGHT,
                    side = VerticalLabelSide.RIGHT,
                    labelsProvider = object : LabelsProvider {
                        override fun provide(
                            min: Float,
                            max: Float
                        ): List<Pair<String, Float>> {
                            return (min.toInt()..(max.toInt() + 1)).map { "${it * 2}" to it.toFloat() }
                        }

                        override fun getMaxLength(): Int = 4

                        override fun getMaxLines(): Int = 1
                    }
                ))

                horizontalAxisLabels(horizontalLabelRenderer)

                grid(
                    gridRenderer(
                        brush = SolidColor(LightGrey),
                        orientation = GridOrientation.HORIZONTAL
                    )
                )
            }
            //endtag=two_axis_chart
        }

        compareScreenshot(composeRule)
    }
}