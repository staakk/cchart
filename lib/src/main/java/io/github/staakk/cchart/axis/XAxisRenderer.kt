package io.github.staakk.cchart.axis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.renderer.RendererContext


class XAxisRenderer(
    private val brush: Brush,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
    private val alpha: Float = 0.2f,
) : AxisRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawLine(
            brush = brush,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            cap = cap,
            alpha = alpha,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height)
        )
    }
}