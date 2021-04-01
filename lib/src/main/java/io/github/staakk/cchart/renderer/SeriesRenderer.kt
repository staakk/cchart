package io.github.staakk.cchart.renderer

import io.github.staakk.cchart.data.Series

/**
 * Renders data series.
 */
fun interface SeriesRenderer {

    fun RendererScope.render(series: Series): List<BoundingShape>
}