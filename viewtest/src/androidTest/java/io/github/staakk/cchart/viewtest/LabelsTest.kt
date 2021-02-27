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
import io.github.staakk.cchart.data.DataBounds
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.label.*
import io.github.staakk.cchart.renderer.lineRenderer
import org.junit.Rule
import org.junit.Test

class LabelsTest : ScreenshotTest {

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
                bounds = DataBounds(0f, 10f, 0f, 5f)
            ) {
                series(
                    seriesOf(
                        "Data",
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
                    renderer = lineRenderer(brush = SolidColor(Color.Blue))
                )

                verticalAxis(verticalAxisRenderer())

                verticalAxis(verticalAxisRenderer(location = VerticalAxisLocation.RIGHT))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxis(horizontalAxisRenderer(location = HorizontalAxisLocation.TOP))

                verticalLabel(verticalLabelRenderer())

                verticalLabel(verticalLabelRenderer(side = VerticalLabelSide.RIGHT))

                verticalLabel(verticalLabelRenderer(location = VerticalLabelLocation.RIGHT))

                verticalLabel(
                    verticalLabelRenderer(
                        location = VerticalLabelLocation.RIGHT,
                        side = VerticalLabelSide.RIGHT
                    )
                )

                horizontalLabel(horizontalLabelRenderer())

                horizontalLabel(horizontalLabelRenderer(side = HorizontalLabelSide.ABOVE))

                horizontalLabel(horizontalLabelRenderer(location = HorizontalLabelLocation.TOP))

                horizontalLabel(
                    horizontalLabelRenderer(
                        location = HorizontalLabelLocation.TOP,
                        side = HorizontalLabelSide.ABOVE
                    )
                )
            }
        }

        compareScreenshot(composeRule)
    }
}