package io.github.staakk.cchart.renderer.point

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import io.github.staakk.cchart.renderer.PointsRenderer
import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.PrimitiveStyle

class DrawPoints(
    private val pointSize: Size = Size(30f, 30f),
    private val style: PrimitiveStyle = PrimitiveStyle(),
) : PointsRenderer {

    override fun RendererScope.draw(rendererPoints: List<RendererPoint<*>>) = clipRect {
        rendererPoints.forEach {
            with(style) {
                drawCircle(
                    radius = pointSize.height / 2,
                    center = it.toOffset(),
                    alpha = alpha,
                    brush = brush,
                    style = style,
                    colorFilter = colorFilter,
                    blendMode = blendMode,
                )
            }
        }
    }
}