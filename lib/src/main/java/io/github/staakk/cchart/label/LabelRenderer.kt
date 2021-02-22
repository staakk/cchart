package io.github.staakk.cchart.label

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

interface LabelRenderer {
    fun DrawScope.render(context: RendererContext)

    fun getMaxLabelSize(): Size

    fun getNormalisedPosition(): Float
}

interface HorizontalLabelRenderer : LabelRenderer

interface VerticalLabelRenderer : LabelRenderer

object NullLabelRenderer : HorizontalLabelRenderer, VerticalLabelRenderer {
    override fun DrawScope.render(context: RendererContext) = Unit
    override fun getMaxLabelSize(): Size = Size.Zero
    override fun getNormalisedPosition(): Float = 0f
}