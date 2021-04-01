package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.circleDrawer
import io.github.staakk.cchart.renderer.circleWithError
import io.github.staakk.cchart.renderer.pointRenderer
import org.junit.Rule
import org.junit.Test

class PointWithErrorChartTest : ScreenshotTest {
    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun pointWithError() {
        composeRule.setContent {
            val horizontalLabelRenderer = horizontalLabelRenderer()
            val verticalLabelRenderer = verticalLabelRenderer()
            Chart(
                modifier = Modifier
                    .padding(start = 32.dp, bottom = 32.dp)
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        pointOf(2f, 1.5f, 0.5f, 0.3f),
                        pointOf(4f, 3.5f, 0.5f, 0.3f),
                        pointOf(6f, 1.3f, 0.5f, 0.3f),
                        pointOf(8f, 4.5f, 0.5f, 0.3f),
                    ),
                    renderer = pointRenderer(
                        pointDrawer = circleWithError(
                            circleDrawer = circleDrawer(brush = SolidColor(Color.Blue))
                        )
                    )
                )

                verticalAxis(verticalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer())

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)
            }
        }

        compareScreenshot(composeRule)
    }
}