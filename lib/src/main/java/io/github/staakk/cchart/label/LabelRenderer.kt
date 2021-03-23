package io.github.staakk.cchart.label

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

fun interface LabelRenderer {
    fun DrawScope.render(context: RendererContext)
}

fun interface HorizontalLabelRenderer : LabelRenderer

fun interface VerticalLabelRenderer : LabelRenderer