package io.github.staakk.cchart.viewtest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.util.Alignment
import io.github.staakk.cchart.util.TextAlignment
import io.github.staakk.cchart.util.drawText
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DrawTextTest : ScreenshotTest {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun parameters(): List<Array<Any>> {
            val alignments = listOf(
                Alignment.BottomLeft to "BottomLeft",
                Alignment.BottomCenter to "BottomCenter",
                Alignment.BottomRight to "BottomRight",
                Alignment.CenterLeft to "CenterLeft",
                Alignment.Center to "Center",
                Alignment.CenterRight to "CenterRight",
                Alignment.TopLeft to "TopLeft",
                Alignment.TopCenter to "TopCenter",
                Alignment.TopRight to "TopRight"
            )
            val textAlignment = listOf(
                TextAlignment.Left to "Left",
                TextAlignment.Center to "Center",
                TextAlignment.Right to "Right"
            )
            return alignments.flatMap { alignment ->
                textAlignment.map { textAlignment ->
                    arrayOf(alignment, textAlignment)
                }
            }
        }
    }

    @Parameterized.Parameter
    lateinit var alignment: Pair<Alignment, String>

    @Parameterized.Parameter(value = 1)
    lateinit var textAlignment: Pair<TextAlignment, String>

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun drawText() {
        composeRule.setContent {
            val density = LocalDensity.current
            val midPx = with(density) { 50.dp.toPx() }
            Surface(modifier = Modifier.size(100.dp, 100.dp)) {
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
                        text = "qde\nasdayf\nwer\npoi",
                        position = with(density) { Offset(50.dp.toPx(), 50.dp.toPx()) },
                        alignment = alignment.first,
                        textAlignment = textAlignment.first,
                        paint = Paint()
                    )
                }
            }
        }

        compareScreenshot(
            composeRule,
            name = "drawText_${alignment.second}_${textAlignment.second}"
        )
    }
}