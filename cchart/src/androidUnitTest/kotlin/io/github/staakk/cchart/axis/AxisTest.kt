package io.github.staakk.cchart.axis

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.viewtest.createViewPaparazziRule
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test


class AxisTest {
    @get:Rule
    val paparazzi = createViewPaparazziRule()

    private fun testAxis(axis: Axis) {
        paparazzi.snapshot {
            Chart(
                modifier = Modifier.size(10.dp),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                features(axis)
            }
        }
    }

    @Test
    fun `should render vertical axis at 0 percent`() = testAxis(
        Axis(
            orientation = AxisOrientation.Vertical,
            positionPercent = 0.0f,
            style = LineStyle(
                strokeWidth = 2f,
                alpha = 1.0f,
            ),
        )
    )

    @Test
    fun `should render vertical axis at 100 percent`() = testAxis(
        Axis(
            orientation = AxisOrientation.Vertical,
            positionPercent = 1.0f,
            style = LineStyle(
                strokeWidth = 2f,
                alpha = 1.0f,
            ),
        )
    )

    @Test
    fun `should render horizontal axis at 0 percent`() = testAxis(
        Axis(
            orientation = AxisOrientation.Horizontal,
            positionPercent = 0.0f,
            style = LineStyle(
                strokeWidth = 2f,
                alpha = 1.0f,
            ),
        )
    )

    @Test
    fun `should render horizontal axis at 100 percent`() = testAxis(
        Axis(
            orientation = AxisOrientation.Horizontal,
            positionPercent = 1.0f,
            style = LineStyle(
                strokeWidth = 2f,
                alpha = 1.0f,
            ),
        )
    )
}