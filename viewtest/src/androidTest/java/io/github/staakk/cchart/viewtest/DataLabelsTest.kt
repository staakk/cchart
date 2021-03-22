package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.HorizontalAlignment
import io.github.staakk.cchart.VerticalAlignment
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.drawCircle
import io.github.staakk.cchart.renderer.pointRenderer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DataLabelsTest : ScreenshotTest {

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun parameters() = HorizontalAlignment.values().flatMap { horizontalAlignment ->
            VerticalAlignment.values().map { verticalAlignment -> horizontalAlignment to verticalAlignment }
        }

    }

    @Parameterized.Parameter
    lateinit var alignments: Pair<HorizontalAlignment, VerticalAlignment>

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun dataLabels() {
        composeRule.setContent {
            val horizontalLabelRenderer = horizontalLabelRenderer()
            val verticalLabelRenderer = verticalLabelRenderer()
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        pointOf(2f, 1.5f),
                        pointOf(4f, 3.5f),
                        pointOf(6f, 1.3f),
                        pointOf(8f, 4.5f),
                    ),
                    renderer = pointRenderer(circleDrawer = drawCircle(brush = SolidColor(Color.Blue)))
                )

                verticalAxis(verticalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer())

                verticalAxisLabels(verticalLabelRenderer)

                horizontalAxisLabels(horizontalLabelRenderer)

                dataLabels {
                    Text(
                        modifier = Modifier.align(alignments.first, alignments.second),
                        text = "(${point.x}, ${point.y})"
                    )
                }
            }
        }

        compareScreenshot(composeRule, name = "dataLabels_${alignments.first}_${alignments.second}")
    }
}