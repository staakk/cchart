package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.ChartContext

class RendererScope(
    drawScope: DrawScope,
    val chartContext: ChartContext
): DrawScope by drawScope