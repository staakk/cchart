package io.github.staakk.composechart.axis

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.composechart.renderer.RendererContext

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