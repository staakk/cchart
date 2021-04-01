package io.github.staakk.cchart.label

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.ChartContext

fun interface LabelRenderer {
    fun DrawScope.render(context: ChartContext)
}

fun interface HorizontalLabelRenderer : LabelRenderer

fun interface VerticalLabelRenderer : LabelRenderer