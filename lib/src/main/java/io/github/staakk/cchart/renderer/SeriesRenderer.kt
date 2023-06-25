package io.github.staakk.cchart.renderer

import io.github.staakk.cchart.data.Series

/**
 * Renders data series and returns bounding boxes of the rendered shapes.
 */
fun interface SeriesRenderer {

    fun RendererScope.render(series: Series): List<BoundingShape>
}