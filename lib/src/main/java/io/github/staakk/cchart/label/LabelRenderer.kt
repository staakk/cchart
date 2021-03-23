package io.github.staakk.cchart.label

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

interface LabelRenderer {
    fun DrawScope.render(context: RendererContext)

    fun getNormalisedPosition(): Float
}

interface HorizontalLabelRenderer : LabelRenderer

interface VerticalLabelRenderer : LabelRenderer