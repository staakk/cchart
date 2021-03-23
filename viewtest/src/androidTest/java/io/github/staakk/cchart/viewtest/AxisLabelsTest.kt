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
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.*
import io.github.staakk.cchart.renderer.drawLine
import io.github.staakk.cchart.renderer.lineRenderer
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
            val verticalLabels = verticalLabelRenderer()
            val rightSideVerticalLabels = verticalLabelRenderer(side = VerticalLabelSide.RIGHT)
            val rightLocationVerticalLabels =
                verticalLabelRenderer(location = VerticalLabelLocation.RIGHT)
            val rightSideRightLocationVerticalLabels = verticalLabelRenderer(
                location = VerticalLabelLocation.RIGHT,
                side = VerticalLabelSide.RIGHT
            )
            val horizontalLabels = horizontalLabelRenderer()
            val aboveHorizontalLabels = horizontalLabelRenderer(side = HorizontalLabelSide.ABOVE)
            val topHorizontalLabels =
                horizontalLabelRenderer(location = HorizontalLabelLocation.TOP)
            val topAboveHorizontalLabels = horizontalLabelRenderer(
                location = HorizontalLabelLocation.TOP,
                side = HorizontalLabelSide.ABOVE
            )
            Chart(
                modifier = Modifier
                    .padding(32.dp)
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
                    renderer = lineRenderer(lineDrawer = drawLine(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = 1f))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = 0f))

                verticalAxisLabels(verticalLabels)

                verticalAxisLabels(rightSideVerticalLabels)

                verticalAxisLabels(rightLocationVerticalLabels)

                verticalAxisLabels(rightSideRightLocationVerticalLabels)

                horizontalAxisLabels(horizontalLabels)

                horizontalAxisLabels(aboveHorizontalLabels)

                horizontalAxisLabels(topHorizontalLabels)

                horizontalAxisLabels(topAboveHorizontalLabels)
            }
        }

        compareScreenshot(composeRule)
    }

    @Test
    fun multiLineLabels() {
        composeRule.setContent {
            val verticalLabels = verticalLabelRenderer(labelsProvider = MultiLineLabelsProvider)
            val rightSideVerticalLabels = verticalLabelRenderer(
                side = VerticalLabelSide.RIGHT,
                labelsProvider = MultiLineLabelsProvider
            )
            val rightLocationVerticalLabels =
                verticalLabelRenderer(
                    location = VerticalLabelLocation.RIGHT,
                    labelsProvider = MultiLineLabelsProvider
                )
            val rightSideRightLocationVerticalLabels = verticalLabelRenderer(
                location = VerticalLabelLocation.RIGHT,
                side = VerticalLabelSide.RIGHT,
                labelsProvider = MultiLineLabelsProvider
            )
            val horizontalLabels = horizontalLabelRenderer(labelsProvider = MultiLineLabelsProvider)
            val aboveHorizontalLabels = horizontalLabelRenderer(
                side = HorizontalLabelSide.ABOVE,
                labelsProvider = MultiLineLabelsProvider
            )
            val topHorizontalLabels =
                horizontalLabelRenderer(
                    location = HorizontalLabelLocation.TOP,
                    labelsProvider = MultiLineLabelsProvider
                )
            val topAboveHorizontalLabels = horizontalLabelRenderer(
                location = HorizontalLabelLocation.TOP,
                side = HorizontalLabelSide.ABOVE,
                labelsProvider = MultiLineLabelsProvider
            )
            Chart(
                modifier = Modifier
                    .padding(48.dp)
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
                    renderer = lineRenderer(lineDrawer = drawLine(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = 1f))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = 0f))

                verticalAxisLabels(verticalLabels)

                verticalAxisLabels(rightSideVerticalLabels)

                verticalAxisLabels(rightLocationVerticalLabels)

                verticalAxisLabels(rightSideRightLocationVerticalLabels)

                horizontalAxisLabels(horizontalLabels)

                horizontalAxisLabels(aboveHorizontalLabels)

                horizontalAxisLabels(topHorizontalLabels)

                horizontalAxisLabels(topAboveHorizontalLabels)

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