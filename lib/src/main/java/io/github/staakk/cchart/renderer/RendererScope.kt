package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope

// TODO merge this class with ChartContext so it's a draw scope + additional information
class RendererScope(
    drawScope: DrawScope,
    val chartContext: ChartContext
): DrawScope by drawScope