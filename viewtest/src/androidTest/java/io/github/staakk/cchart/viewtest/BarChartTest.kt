package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.barRenderer
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
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                bounds = DataBounds(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        "Data",
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
                    renderer = barRenderer(
                        brushProvider = { SolidColor(Color.Blue) },
                        preferredWidth = 50f
                    )
                )

                verticalAxis(verticalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer())

                verticalLabel(verticalLabelRenderer())

                horizontalLabel(horizontalLabelRenderer())
            }
        }

        compareScreenshot(composeRule)
    }

    @Test
    fun multiSeriesBarChart() {
        composeRule.setContent {
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                bounds = DataBounds(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        "Data_1",
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
                    seriesOf(
                        "Data_2",
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
                    renderer = barRenderer(
                        brushProvider = {
                            val color = when (it) {
                                "Data_1" -> Color.Blue
                                "Data_2" -> Color.Green
                                else -> Color.Red
                            }
                            SolidColor(color)
                        },
                        preferredWidth = 50f
                    )
                )

                verticalAxis(verticalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer())

                verticalLabel(verticalLabelRenderer())

                horizontalLabel(horizontalLabelRenderer())
            }
        }

        compareScreenshot(composeRule)
    }

    @Test
    fun multiSeriesMinimalSpacingBarChart() {
        composeRule.setContent {
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                bounds = DataBounds(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        "Data_1",
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
                    seriesOf(
                        "Data_2",
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
                    renderer = barRenderer(
                        brushProvider = {
                            val color = when (it) {
                                "Data_1" -> Color.Blue
                                "Data_2" -> Color.Green
                                else -> Color.Red
                            }
                            SolidColor(color)
                        },
                        preferredWidth = 50f,
                        minimalSpacing = 50f
                    )
                )

                verticalAxis(verticalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer())

                verticalLabel(verticalLabelRenderer())

                horizontalLabel(horizontalLabelRenderer())
            }
        }

        compareScreenshot(composeRule)
    }
}