package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.label.defaultHorizontalLabelRenderer
import io.github.staakk.cchart.label.defaultVerticalLabelRenderer
import io.github.staakk.cchart.renderer.point.DrawPoints
import io.github.staakk.cchart.style.PrimitiveStyle
import io.github.staakk.cchart.verticalAxis
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DataLabelsTest {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun parameters() = listOf(
            Alignment.TopStart,
            Alignment.TopCenter,
            Alignment.TopEnd,
            Alignment.CenterStart,
            Alignment.Center,
            Alignment.CenterEnd,
            Alignment.BottomStart,
            Alignment.BottomCenter,
            Alignment.BottomEnd,
        )
    }

    @Parameterized.Parameter
    lateinit var alignment: Alignment

    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun dataLabels() {
        paparazzi.snapshot {
            val labels = listOf(
                defaultHorizontalLabelRenderer(),
                defaultVerticalLabelRenderer(),
            )
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        pointOf(2f, 1.5f),
                        pointOf(4f, 3.5f),
                        pointOf(6f, 1.3f),
                        pointOf(8f, 4.5f),
                    ),
                    DrawPoints(
                        Size(10f, 10f),
                        PrimitiveStyle(brush = SolidColor(Color.Blue)),
                    ),
                )

                verticalAxis()
                horizontalAxis()

                labels.forEach { label(it) }

                dataLabels {
                    Text(
                        modifier = Modifier.align(alignment),
                        text = "(${data.x}, ${data.y})"
                    )
                }
            }
        }
    }
}