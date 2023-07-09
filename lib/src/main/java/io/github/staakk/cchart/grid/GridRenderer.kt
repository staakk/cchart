package io.github.staakk.cchart.grid

import io.github.staakk.cchart.renderer.RendererScope

/**
 * Renders grid on the [io.github.staakk.cchart.Chart].
 */
interface GridRenderer {

    /**
     * Draws grid.
     */
    fun RendererScope.render()
}