package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.grid.GridLinesProviders
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.GridRenderer
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.renderer.drawLine
import io.github.staakk.cchart.renderer.lineRenderer
import org.junit.Rule
import org.junit.Test

class GridsTest : ScreenshotTest {

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun verticalGrid() = runTestFor(gridRenderer(orientation = GridOrientation.VERTICAL))

    @Test
    fun horizontalGrid() = runTestFor(gridRenderer(orientation = GridOrientation.HORIZONTAL))

    @Test
    fun verticalAndHorizontal() = runTestFor(
        gridRenderer(orientation = GridOrientation.VERTICAL),
        gridRenderer(orientation = GridOrientation.HORIZONTAL)
    )

    @Test
    fun multipleVertical() = runTestFor(
        gridRenderer(
            orientation = GridOrientation.VERTICAL,
            gridLinesProvider = GridLinesProviders.multiple(0.5f)
        ),
        gridRenderer(
            brush = SolidColor(Color.Red),
            orientation = GridOrientation.VERTICAL,
            gridLinesProvider = GridLinesProviders.multiple(1f / 3f)
        ),
    )

    @Test
    fun multipleHorizontal() = runTestFor(
        gridRenderer(
            orientation = GridOrientation.HORIZONTAL,
            gridLinesProvider = GridLinesProviders.multiple(0.5f)
        ),
        gridRenderer(
            brush = SolidColor(Color.Red),
            orientation = GridOrientation.HORIZONTAL,
            gridLinesProvider = GridLinesProviders.multiple(1f / 3f)
        ),
    )

    private fun runTestFor(vararg grids: GridRenderer) {
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
                    renderer = lineRenderer(lineDrawer = drawLine(brush = SolidColor(Color.Blue)))
                )

                grids.forEach(::grid)
            }
        }

        compareScreenshot(composeRule)
    }
}