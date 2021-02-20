package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Series

class CompositeSeriesRenderer(
    private val renderers: List<SeriesRenderer>
) : SeriesRenderer {

    override fun DrawScope.render(context: RendererContext, series: Series) {
        renderers.forEach { with(it) { render(context, series) } }
    }
}

fun combine(vararg renderers: SeriesRenderer): SeriesRenderer =
    CompositeSeriesRenderer(renderers.toList())