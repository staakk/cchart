package io.github.staakk.cchart.axis

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

interface AxisRenderer {
    fun DrawScope.render(context: RendererContext)
}

object NullAxisRenderer : AxisRenderer {
    override fun DrawScope.render(context: RendererContext) {
    }
}