package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset

/**
 * Creates renderer for horizontal axis.
 */
fun horizontalAxisRenderer(
    location: Float = HorizontalAxisRenderer.Bottom,
    axisDrawer: AxisDrawer = axisDrawer()
) = HorizontalAxisRenderer {
    val yPos = location * size.height
    with(axisDrawer) {
        draw(
            start = Offset(0f, yPos),
            end = Offset(size.width, yPos)
        )
    }
}