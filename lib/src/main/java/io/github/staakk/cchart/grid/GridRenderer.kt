package io.github.staakk.cchart.grid

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.ChartContext

/**
 * Renders grid on the [io.github.staakk.cchart.Chart].
 */
interface GridRenderer {

    /**
     * Draws grid.
     */
    fun DrawScope.render(context: ChartContext)
}