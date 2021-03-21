package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.HorizontalAxisLocation
import io.github.staakk.cchart.axis.VerticalAxisLocation
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.*
import io.github.staakk.cchart.renderer.lineRenderer
import io.github.staakk.cchart.renderer.renderLine
import org.junit.Rule
import org.junit.Test

class AxisLabelsTest : ScreenshotTest {

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun labels() {
        composeRule.setContent {
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        pointOf(0f, 1f),
                        pointOf(2f, 1.5f),
                        pointOf(3f, 4f),
                        pointOf(4f, 3.5f),
                        pointOf(5f, 2f),
                        pointOf(6f, 1.3f),
                        pointOf(7f, 4f),
                        pointOf(8f, 4.5f),
                        pointOf(9f, 4.7f),
                    ),
                    renderer = lineRenderer(render = renderLine(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = VerticalAxisLocation.RIGHT))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = HorizontalAxisLocation.TOP))

                verticalAxisLabels(verticalLabelRenderer())

                verticalAxisLabels(verticalLabelRenderer(side = VerticalLabelSide.RIGHT))

                verticalAxisLabels(verticalLabelRenderer(location = VerticalLabelLocation.RIGHT))

                verticalAxisLabels(
                    verticalLabelRenderer(
                        location = VerticalLabelLocation.RIGHT,
                        side = VerticalLabelSide.RIGHT
                    )
                )

                horizontalAxisLabels(horizontalLabelRenderer())

                horizontalAxisLabels(horizontalLabelRenderer(side = HorizontalLabelSide.ABOVE))

                horizontalAxisLabels(horizontalLabelRenderer(location = HorizontalLabelLocation.TOP))

                horizontalAxisLabels(
                    horizontalLabelRenderer(
                        location = HorizontalLabelLocation.TOP,
                        side = HorizontalLabelSide.ABOVE
                    )
                )
            }
        }

        compareScreenshot(composeRule)
    }

    @Test
    fun multiLineLabels() {
        composeRule.setContent {
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        pointOf(0f, 1f),
                        pointOf(2f, 1.5f),
                        pointOf(3f, 4f),
                        pointOf(4f, 3.5f),
                        pointOf(5f, 2f),
                        pointOf(6f, 1.3f),
                        pointOf(7f, 4f),
                        pointOf(8f, 4.5f),
                        pointOf(9f, 4.7f),
                    ),
                    renderer = lineRenderer(render = renderLine(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = VerticalAxisLocation.RIGHT))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = HorizontalAxisLocation.TOP))

                verticalAxisLabels(verticalLabelRenderer(labelsProvider = MultiLineLabelsProvider))

                verticalAxisLabels(
                    verticalLabelRenderer(
                        side = VerticalLabelSide.RIGHT,
                        labelsProvider = MultiLineLabelsProvider
                    )
                )

                verticalAxisLabels(
                    verticalLabelRenderer(
                        location = VerticalLabelLocation.RIGHT,
                        labelsProvider = MultiLineLabelsProvider
                    )
                )

                verticalAxisLabels(
                    verticalLabelRenderer(
                        location = VerticalLabelLocation.RIGHT,
                        side = VerticalLabelSide.RIGHT,
                        labelsProvider = MultiLineLabelsProvider
                    )
                )

                horizontalAxisLabels(horizontalLabelRenderer(labelsProvider = MultiLineLabelsProvider))

                horizontalAxisLabels(
                    horizontalLabelRenderer(
                        side = HorizontalLabelSide.ABOVE,
                        labelsProvider = MultiLineLabelsProvider
                    )
                )

                horizontalAxisLabels(
                    horizontalLabelRenderer(
                        location = HorizontalLabelLocation.TOP,
                        labelsProvider = MultiLineLabelsProvider
                    )
                )

                horizontalAxisLabels(
                    horizontalLabelRenderer(
                        location = HorizontalLabelLocation.TOP,
                        side = HorizontalLabelSide.ABOVE,
                        labelsProvider = MultiLineLabelsProvider
                    )
                )

                grid(gridRenderer())
            }
        }

        compareScreenshot(composeRule)
    }

    private object MultiLineLabelsProvider : LabelsProvider {
        override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
            return (min.toInt()..(max.toInt() + 1)).map { "$it\n$it$it" to it.toFloat() }
        }

        override fun getMaxLength(): Int = 10

        override fun getMaxLines(): Int = 2
    }
}