package io.github.staakk.cchart.axis

import androidx.compose.foundation.layout.aspectRatio
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

@Composable
@Suppress("unused", "TestFunctionName")
fun AxisSample() {
    val strokeWidth = with(LocalDensity.current) { 2.dp.toPx() }
    Chart(
        modifier = Modifier.aspectRatio(1f, false),
        viewport = Viewport(0f, 10f, 0f, 5f)
    ) {
        // Rest of the chart configuration

        features(
            // Creates horizontal axis at the left side with 2dp width and black color.
            Axis(
                orientation = AxisOrientation.Horizontal,
                positionPercent = 0f,
                style = LineStyle(
                    strokeWidth = strokeWidth,
                    brush = SolidColor(Color.Black),
                )
            )
        )
    }
}