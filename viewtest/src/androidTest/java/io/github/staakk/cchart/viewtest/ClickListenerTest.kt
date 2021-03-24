package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.rule.GrantPermissionRule
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.renderer.drawCircle
import io.github.staakk.cchart.renderer.pointRenderer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ClickListenerTest {

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun shouldRegisterClickOnChart() {
        var data: Data? = null
        composeRule.setContent {
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false)
                    .testTag("chart"),
                viewport = Viewport(0f, 10f, 0f, 10f),
                onClick = { _, receivedData ->
                    data = receivedData
                }
            ) {
                series(
                    seriesOf(
                        pointOf(0f, 0f),
                        pointOf(2f, 2f),
                        pointOf(4f, 4f),
                        pointOf(6f, 6f),
                        pointOf(8f, 8f),
                        pointOf(10f, 10f),
                    ),
                    renderer = pointRenderer(pointDrawer = drawCircle(brush = SolidColor(Color.Blue)))
                )
            }
        }

        composeRule.onNodeWithTag("chart")
            .performGesture {
                click(position = center)
            }

        assertEquals(pointOf(5f, 5f), data)
    }

    @Test
    fun shouldRegisterClickOnData() {
        var data: Data? = null
        val point40percent = pointOf(4f, 4f, "tag")
        composeRule.setContent {
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false)
                    .testTag("chart"),
                viewport = Viewport(0f, 10f, 0f, 10f),
                onClick = { _, receivedData ->
                    data = receivedData
                }
            ) {
                series(
                    seriesOf(
                        pointOf(0f, 0f),
                        pointOf(2f, 2f),
                        point40percent,
                        pointOf(6f, 6f),
                        pointOf(8f, 8f),
                        pointOf(10f, 10f),
                    ),
                    renderer = pointRenderer(pointDrawer = drawCircle(brush = SolidColor(Color.Blue)))
                )
            }
        }

        composeRule.onNodeWithTag("chart")
            .performGesture {
                click(percentOffset(0.4f, 0.6f))
            }

        assertEquals(point40percent, data)
    }
}


