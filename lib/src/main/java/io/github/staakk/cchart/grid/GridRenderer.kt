package io.github.staakk.cchart.grid

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

interface GridRenderer {

    fun DrawScope.render(context: RendererContext)
}