package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.pointRenderer
import org.junit.Rule
import org.junit.Test

class AnchoredContentTest : ScreenshotTest {
    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun anchoredContentTest() {
        composeRule.setContent {
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
                    renderer = pointRenderer(pointDrawer = circleDrawer(brush = SolidColor(Color.Blue)))
                )

                anchor(pointOf(5f, 5f)) {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .align(HorizontalAlignment.CENTER, VerticalAlignment.CENTER)
                            .clip(RectangleShape)
                            .background(Color.Green)
                    )
                }
            }
        }

        compareScreenshot(composeRule)
    }
}