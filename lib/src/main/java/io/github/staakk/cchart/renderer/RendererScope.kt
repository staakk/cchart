package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope

class RendererScope(
    drawScope: DrawScope,
    val chartContext: ChartContext
): DrawScope by drawScope