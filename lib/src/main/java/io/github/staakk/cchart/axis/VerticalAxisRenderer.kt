package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.style.LineStyle

/**
 * Creates renderer for vertical axis.
 *
 * @param location Location of the axis in percents.
 * @param axisDrawer Function drawing the axis.
 */
fun verticalAxisRenderer(
    location: Float = VerticalAxisRenderer.Left,
    axisDrawer: AxisDrawer = axisDrawer(LineStyle())
) = VerticalAxisRenderer {
    val xPos = location * size.width
    with(axisDrawer) {
        draw(
            start = Offset(xPos, 0f),
            end = Offset(xPos, size.height)
        )
    }
}