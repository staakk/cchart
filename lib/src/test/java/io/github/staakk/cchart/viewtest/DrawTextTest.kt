package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.common.drawText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DrawTextTest {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun parameters(): List<Pair<Alignment, String>> = listOf(
            Alignment.BottomStart to "BottomStart",
            Alignment.BottomCenter to "BottomCenter",
            Alignment.BottomEnd to "BottomEnd",
            Alignment.CenterStart to "CenterStart",
            Alignment.Center to "Center",
            Alignment.CenterEnd to "CenterEnd",
            Alignment.TopStart to "TopStart",
            Alignment.TopCenter to "TopCenter",
            Alignment.TopEnd to "TopEnd"
        )
    }

    @Parameterized.Parameter
    lateinit var legacyAlignment: Pair<Alignment, String>

    @get:Rule
    val paparazzi = createFullScreenPaparazziRule()

    @Test
    fun drawText() {
        paparazzi.snapshot {
            val density = LocalDensity.current
            val midPx = with(density) { 50.dp.toPx() }
            Surface(modifier = Modifier.size(100.dp, 100.dp)) {
                val textMeasurer = rememberTextMeasurer()
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLine(
                        color = Color.Blue,
                        start = Offset(0f, midPx),
                        end = Offset(midPx * 2, midPx)
                    )
                    drawLine(
                        color = Color.Blue,
                        start = Offset(midPx, 0f),
                        end = Offset(midPx, midPx * 2)
                    )
                    drawText(
                        textMeasurer = textMeasurer,
                        text = "qde\nasdayf\nwer\npoi",
                        position = with(density) { Offset(50.dp.toPx(), 50.dp.toPx()) },
                        alignment = legacyAlignment.first,
                    )
                }
            }
        }
    }
}