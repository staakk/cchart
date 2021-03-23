package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.renderer.RendererContext

/**
 * Interface for axis renderers.
 */
fun interface AxisRenderer {
    /**
     * Function called when axis needs to be drawn.
     */
    fun DrawScope.render(context: RendererContext)
}

fun interface HorizontalAxisRenderer : AxisRenderer

fun interface VerticalAxisRenderer : AxisRenderer

fun interface AxisDrawer {
    fun DrawScope.draw(start: Offset, end: Offset)
}

fun axisDrawer(
    brush: Brush = SolidColor(Color.Black),
    strokeWidth: Float = Stroke.HairlineWidth,
    alpha: Float = 1f,
    pathEffect: PathEffect? = null,
    cap: StrokeCap = Stroke.DefaultCap,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
) = AxisDrawer { start, end ->
    drawLine(
        brush = brush,
        strokeWidth = strokeWidth,
        pathEffect = pathEffect,
        cap = cap,
        alpha = alpha,
        start = start,
        end = end,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
}

/**
 * Creates renderer for horizontal axis.
 */
fun horizontalAxisRenderer(
    location: Float = 1f,
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

/**
 * Creates renderer for vertical axis.
 */
fun verticalAxisRenderer(
    location: Float = 0f,
    axisDrawer: AxisDrawer = axisDrawer()
) = VerticalAxisRenderer {
    val xPos = location * size.width
    with(axisDrawer) {
        draw(
            start = Offset(xPos, 0f),
            end = Offset(xPos, size.height)
        )
    }
}