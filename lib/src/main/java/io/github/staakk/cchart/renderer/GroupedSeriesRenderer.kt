package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.GroupedSeries

fun interface GroupedSeriesRenderer {

    fun DrawScope.render(context: RendererContext, series: GroupedSeries): List<BoundingShape>
}