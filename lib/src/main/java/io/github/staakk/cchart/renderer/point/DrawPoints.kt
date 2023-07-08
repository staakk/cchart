package io.github.staakk.cchart.renderer.point

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.renderer.Drawer
import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.PrimitiveStyle

class DrawPoints(
    private val pointSize: Size = Size(30f, 30f),
    private val style: PrimitiveStyle = PrimitiveStyle(),
) : Drawer {

    override fun RendererScope.draw(index: Int, rendererPoints: List<RendererPoint<*>>) {
        with(style) {
            drawCircle(
                radius = pointSize.height / 2,
                center = rendererPoints[index].toOffset(),
                alpha = alpha,
                brush = brush,
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode,
            )
        }
    }
}