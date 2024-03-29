package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.point.DrawPoints
import io.github.staakk.cchart.style.PrimitiveStyle
import org.junit.Rule
import org.junit.Test

class AnchoredContentTest {
    @get:Rule
    val paparazzi = createFullScreenPaparazziRule()

    @Test
    fun anchoredContentTest() {
        paparazzi.snapshot {
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 10f)
            ) {
                series(
                    seriesOf(
                        pointOf(1f, 1f),
                        pointOf(2f, 2f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                        pointOf(5f, 5f),
                        pointOf(6f, 6f),
                        pointOf(7f, 7f),
                        pointOf(8f, 8f),
                        pointOf(9f, 9f),
                    ),
                    DrawPoints(
                        Size(10f, 10f),
                        PrimitiveStyle(brush = SolidColor(Color.Blue))
                    )
                )

                anchor(pointOf(5f, 5f)) {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.Center)
                            .clip(RectangleShape)
                            .background(Color.Green)
                    )
                }
            }
        }
    }
}