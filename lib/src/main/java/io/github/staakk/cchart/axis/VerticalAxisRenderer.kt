package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.renderer.RendererContext


private class VerticalAxisRenderer(
    private val brush: Brush,
    private val location: VerticalAxisLocation = VerticalAxisLocation.LEFT,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
    private val alpha: Float = 0.2f,
    private val colorFilter: ColorFilter? = null,
    private val blendMode: BlendMode = DrawScope.DefaultBlendMode,
) : AxisRenderer {

    override fun DrawScope.render(context: RendererContext) {
        val xPos = when (location) {
            VerticalAxisLocation.RIGHT -> size.width
            VerticalAxisLocation.LEFT -> 0f
        }
        drawLine(
            brush = brush,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            cap = cap,
            alpha = alpha,
            start = Offset(xPos, 0f),
            end = Offset(xPos, size.height),
            colorFilter = colorFilter,
            blendMode = blendMode
        )
    }
}

/**
 * Determines vertical axis location.
 */
enum class VerticalAxisLocation {
    /**
     * Draws axis on the right side of the chart.
     */
    RIGHT,

    /**
     * Draw axis on the left side of the chart.
     */
    LEFT
}

fun verticalAxisRenderer(
    brush: Brush = SolidColor(Color.Black),
    location: VerticalAxisLocation = VerticalAxisLocation.LEFT,
    strokeWidth: Float = Stroke.HairlineWidth,
    pathEffect: PathEffect? = null,
    cap: StrokeCap = Stroke.DefaultCap,
    alpha: Float = 0.2f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): AxisRenderer = VerticalAxisRenderer(
    brush,
    location,
    strokeWidth,
    pathEffect,
    cap,
    alpha,
    colorFilter,
    blendMode
)