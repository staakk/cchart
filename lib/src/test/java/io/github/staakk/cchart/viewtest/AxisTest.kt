package io.github.staakk.cchart.viewtest

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.renderer.axis.Axis
import io.github.staakk.cchart.renderer.axis.AxisOrientation.Companion.Horizontal
import io.github.staakk.cchart.renderer.axis.AxisOrientation.Companion.Vertical
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.features
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
                features(
                    Axis(Horizontal, 0.0f),
                    Axis(Horizontal, 1.0f),
                    Axis(Vertical, 0.0f),
                    Axis(Vertical, 1.0f),
                )
            }
        }
    }

}