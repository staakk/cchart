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
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.barDrawer
import io.github.staakk.cchart.renderer.barGroupRenderer
import org.junit.Rule
import org.junit.Test

class BarChartTest : ScreenshotTest {

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun barChart() {
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
                    groupedSeriesOf(
                        pointOf(1f, 1f),
                        pointOf(2f, 1.5f),
                        pointOf(3f, 4f),
                        pointOf(4f, 3.5f),
                        pointOf(5f, 2f),
                        pointOf(6f, 1.3f),
                        pointOf(7f, 4f),
                        pointOf(8f, 4.5f),
                        pointOf(9f, 4.7f),
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 50f,
                        barDrawer = barDrawer { _, _ -> SolidColor(Color.Blue) }
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

    @Test
    fun multiSeriesBarChart() {
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
                    groupedSeriesOf(
                        listOf(
                            pointOf(1f, 1f),
                            pointOf(1f, 1f),
                        ),
                        listOf(
                            pointOf(2f, 1.5f),
                            pointOf(2f, 1.5f),
                        ),
                        listOf(
                            pointOf(3f, 4f),
                            pointOf(3f, 4f),
                        ),
                        listOf(
                            pointOf(4f, 3.5f),
                            pointOf(4f, 3.5f),
                        ),
                        listOf(
                            pointOf(5f, 2f),
                            pointOf(5f, 2f),
                        ),
                        listOf(
                            pointOf(6f, 1.3f),
                            pointOf(6f, 1.3f),
                        ),
                        listOf(
                            pointOf(7f, 4f),
                            pointOf(7f, 4f),
                        ),
                        listOf(
                            pointOf(8f, 4.5f),
                            pointOf(8f, 4.5f),
                        ),
                        listOf(
                            pointOf(9f, 4.7f),
                            pointOf(9f, 4.7f),
                        )
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 50f,
                        barDrawer = barDrawer { index, _ ->
                            val color = when (index) {
                                0 -> Color.Blue
                                1 -> Color.Green
                                else -> Color.Red
                            }
                            SolidColor(color)
                        },
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

    @Test
    fun multiSeriesMinimalSpacingBarChart() {
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
                    groupedSeriesOf(
                        listOf(
                            pointOf(1f, 1f),
                            pointOf(1f, 1f),
                        ),
                        listOf(
                            pointOf(2f, 1.5f),
                            pointOf(2f, 1.5f),
                        ),
                        listOf(
                            pointOf(3f, 4f),
                            pointOf(3f, 4f),
                        ),
                        listOf(
                            pointOf(4f, 3.5f),
                            pointOf(4f, 3.5f),
                        ),
                        listOf(
                            pointOf(5f, 2f),
                            pointOf(5f, 2f),
                        ),
                        listOf(
                            pointOf(6f, 1.3f),
                            pointOf(6f, 1.3f),
                        ),
                        listOf(
                            pointOf(7f, 4f),
                            pointOf(7f, 4f),
                        ),
                        listOf(
                            pointOf(8f, 4.5f),
                            pointOf(8f, 4.5f),
                        ),
                        listOf(
                            pointOf(9f, 4.7f),
                            pointOf(9f, 4.7f),
                        )
                    ),
                    renderer = barGroupRenderer(
                        preferredWidth = 50f,
                        minimalSpacing = 50f,
                        barDrawer = barDrawer { index, _ ->
                            val color = when (index) {
                                0 -> Color.Blue
                                1 -> Color.Green
                                else -> Color.Red
                            }
                            SolidColor(color)
                        }
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