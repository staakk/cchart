package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset

/**
 * Creates renderer for horizontal axis.
 *
 * @param location Location of the axis in percents.
 * @param axisDrawer Function drawing the axis.
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