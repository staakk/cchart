package io.github.staakk.cchart.renderer

import io.github.staakk.cchart.data.Series

class CompositeSeriesRenderer(
    private val renderers: List<SeriesRenderer>
) : SeriesRenderer {

    override fun RendererScope.render(series: Series): List<BoundingShape> =
        renderers.flatMap { with(it) { render(series) } }

    companion object {
        /**
         * Combines multiple renderers.
         */
        fun combine(vararg renderers: SeriesRenderer): SeriesRenderer =
            CompositeSeriesRenderer(renderers.flatMap {
                when (it) {
                    is CompositeSeriesRenderer -> it.renderers
                    else -> listOf(it)
                }
            })
    }
}