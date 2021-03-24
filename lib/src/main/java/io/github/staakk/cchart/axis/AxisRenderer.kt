@file:Suppress("unused")

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

fun interface HorizontalAxisRenderer : AxisRenderer {
    companion object {
        const val Top = 0.0f
        const val Bottom = 1.0f
    }
}

fun interface VerticalAxisRenderer : AxisRenderer {
    companion object {
        const val Right = 1f
        const val Left = 0f
    }
}

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