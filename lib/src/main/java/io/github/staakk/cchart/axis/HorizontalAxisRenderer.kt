package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.renderer.RendererContext


private class HorizontalAxisRenderer(
    private val brush: Brush,
    private val location: HorizontalAxisLocation = HorizontalAxisLocation.BOTTOM,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val alpha: Float = 0.2f,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
    private val colorFilter: ColorFilter? = null,
    private val blendMode: BlendMode = DrawScope.DefaultBlendMode,
) : AxisRenderer {

    override fun DrawScope.render(context: RendererContext) {
        val yPos = when (location) {
            HorizontalAxisLocation.TOP -> 0f
            HorizontalAxisLocation.BOTTOM -> size.height
        }

        drawLine(
            brush = brush,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            cap = cap,
            alpha = alpha,
            start = Offset(0f, yPos),
            end = Offset(size.width, yPos),
            colorFilter = colorFilter,
            blendMode = blendMode
        )
    }
}

enum class HorizontalAxisLocation { TOP, BOTTOM }

fun horizontalAxisRenderer(
    brush: Brush = SolidColor(Color.Black),
    location: HorizontalAxisLocation = HorizontalAxisLocation.BOTTOM,
    strokeWidth: Float = Stroke.HairlineWidth,
    alpha: Float = 0.2f,
    pathEffect: PathEffect? = null,
    cap: StrokeCap = Stroke.DefaultCap,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): AxisRenderer = HorizontalAxisRenderer(
    brush,
    location,
    strokeWidth,
    alpha,
    pathEffect,
    cap,
    colorFilter,
    blendMode
)