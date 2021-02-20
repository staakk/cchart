package io.github.staakk.cchart.axis

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

interface AxisRenderer {

    fun DrawScope.renderAxis(context: RendererContext)

    fun DrawScope.renderLabels(context: RendererContext)
}

object NullAxisRenderer : AxisRenderer {
    override fun DrawScope.renderAxis(context: RendererContext) {
    }

    override fun DrawScope.renderLabels(context: RendererContext) {
    }
}