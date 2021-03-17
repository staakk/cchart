package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.renderer.RendererContext


private class VerticalAxisRendererImpl(
    private val brush: Brush,
    private val location: VerticalAxisLocation,
    private val strokeWidth: Float,
    private val pathEffect: PathEffect?,
    private val cap: StrokeCap,
    private val alpha: Float,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
) : VerticalAxisRenderer {

    override fun DrawScope.render(context: RendererContext) {
        val xPos = getNormalisedPosition() * size.width
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

    override fun getNormalisedPosition() = when (location) {
        VerticalAxisLocation.RIGHT -> 1f
        VerticalAxisLocation.LEFT -> 0f
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
    alpha: Float = 1f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): VerticalAxisRenderer = VerticalAxisRendererImpl(
    brush,
    location,
    strokeWidth,
    pathEffect,
    cap,
    alpha,
    colorFilter,
    blendMode
)