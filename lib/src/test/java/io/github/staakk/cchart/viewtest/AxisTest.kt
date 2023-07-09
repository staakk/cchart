package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.horizontalAxis
import io.github.staakk.cchart.verticalAxis
import org.junit.Rule
import org.junit.Test

class AxisTest {
    @get:Rule
    val paparazzi = createPaparazziRule()

    @Test
    fun axis() {
        paparazzi.snapshot {
            Chart(
                modifier = Modifier
                    .aspectRatio(1f, false)
                    .padding(16.dp),
                viewport = Viewport(0f, 10f, 0f, 5f)
            ) {
                verticalAxis()

                verticalAxis(positionPercent = 1f)

                horizontalAxis()

                horizontalAxis(positionPercent = 1f)
            }
        }
    }

}