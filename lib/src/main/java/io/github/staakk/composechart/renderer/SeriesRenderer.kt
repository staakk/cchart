package io.github.staakk.composechart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.composechart.data.Series

fun interface SeriesRenderer {

    fun DrawScope.render(rendererContext: RendererContext, series: Series)
}