package io.github.staakk.cchart.label

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

interface LabelRenderer {
    fun DrawScope.render(context: RendererContext)

    fun getMaxLabelSize(): Size
}

object NullLabelRenderer : LabelRenderer {
    override fun DrawScope.render(context: RendererContext) {
    }

    override fun getMaxLabelSize(): Size = Size.Zero
}