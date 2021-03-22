package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Series

class CompositeSeriesRenderer(
    private val renderers: List<SeriesRenderer>
) : SeriesRenderer {

    override fun DrawScope.render(
        context: RendererContext,
        series: Series
    ): List<BoundingShape> = renderers.flatMap { with(it) { render(context, series) } }

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