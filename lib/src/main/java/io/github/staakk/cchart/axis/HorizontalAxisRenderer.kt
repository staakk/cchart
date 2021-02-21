package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.renderer.RendererContext


class HorizontalAxisRenderer(
    private val brush: Brush,
    private val location: Location = Location.BOTTOM,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
    private val alpha: Float = 0.2f,
) : AxisRenderer {

    enum class Location { TOP, BOTTOM }

    override fun DrawScope.render(context: RendererContext) {
        val yPos = when (location) {
            Location.TOP -> 0f
            Location.BOTTOM -> size.height
        }

        drawLine(
            brush = brush,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            cap = cap,
            alpha = alpha,
            start = Offset(0f, yPos),
            end = Offset(size.width, yPos)
        )
    }
}