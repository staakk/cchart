package io.github.staakk.cchart.readme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.createPaparazziRule
import io.github.staakk.cchart.data.Series
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.grid.Grid
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.label.Labels
import io.github.staakk.cchart.line.DrawLine
import io.github.staakk.cchart.point.DrawPoints
import io.github.staakk.cchart.point.PointBoundsProvider
import io.github.staakk.cchart.style.LabelStyle
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.style.PrimitiveStyle
import org.junit.Rule
import org.junit.Test

private val Blue = Color(0xFF64B5F6)
private val Green = Color(0xFF81C784)
private val LightGrey = Color(0xFF424242)
private val White = Color(0xFFFFFFFF)

private val data = (1..10)
    .map { pointOf(it, it * it) }

class ReadmeImageTest {

    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun recordReadmeImage() {
        paparazzi.snapshot {
            val pointSize: Size
            val strokeWidth: Float
            val axisWidth: Float
            with(LocalDensity.current) {
                pointSize = Size(8.dp.toPx(), 8.dp.toPx())
                strokeWidth = 2.dp.toPx()
                axisWidth = 1.dp.toPx()
            }

            val axisStyle = LineStyle(
                brush = SolidColor(White),
                strokeWidth = axisWidth,
            )
            val labelStyle = LabelStyle(brush = SolidColor(White))
            Surface(modifier = Modifier.background(color = LightGrey)) {

                @OptIn(ExperimentalTextApi::class)
                val labels = arrayOf(
                    Labels.horizontalLabels(
                        labelStyle = labelStyle,
                        labelsProvider = { _, _ ->
                            (0..10 step 2 )
                                .map { it.toString() to it.toFloat() }
                        }
                    ),
                    Labels.verticalLabels(
                        labelStyle = labelStyle.copy(
                            alignment = Alignment.CenterEnd,
                            labelOffset = with(LocalDensity.current) { Offset(-4.dp.toPx(), 0f) },
                        ),
                        labelsProvider = { _, _ ->
                            (10..100 step 10 )
                                .map { it.toString() to it.toFloat() }
                        }
                    ),
                )

                Chart(
                    modifier = Modifier
                        .background(color = LightGrey)
                        .padding(start = 32.dp, bottom = 32.dp)
                        .aspectRatio(1f, false),
                    viewport = Viewport(0f, 11.5f, 0f, 110.5f)
                ) {
                    series(
                        Series(data),
                        DrawLine(
                            LineStyle(
                                brush = SolidColor(Blue),
                                strokeWidth = strokeWidth,
                            )
                        )
                    )

                    series(
                        Series(data),
                        DrawPoints(
                            pointSize,
                            PrimitiveStyle(brush = SolidColor(Green)),
                        ),
                        PointBoundsProvider(Size(1f, 1f)),
                    )

                    features(
                        Axis(AxisOrientation.Horizontal, 0.0f, axisStyle),
                        Axis(AxisOrientation.Vertical, 0.0f, axisStyle),
                        *labels,
                        Grid(
                            orientation = GridOrientation.Horizontal,
                            gridLinesProvider = { _, _ -> (10..100 step 10).map { it.toFloat() } },
                            lineStyle = LineStyle(
                                alpha = 0.3f,
                                brush = SolidColor(White),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 5f)),
                            )
                        ),
                    )

                    dataLabels {
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(bottom = 4.dp),
                            text = "${String.format("%.0f", point.x)}, ${String.format("%.0f", point.y)}",
                            color = White,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}