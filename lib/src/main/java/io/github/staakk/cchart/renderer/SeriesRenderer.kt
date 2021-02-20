package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Series

fun interface SeriesRenderer {

    fun DrawScope.render(context: RendererContext, series: List<Series>)
}