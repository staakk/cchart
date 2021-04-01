package io.github.staakk.cchart.renderer

import io.github.staakk.cchart.data.GroupedSeries

fun interface GroupedSeriesRenderer {

    fun RendererScope.render(series: GroupedSeries): List<BoundingShape>
}