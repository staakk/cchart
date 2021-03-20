package io.github.staakk.cchart.axis

import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.renderer.RendererContext

/**
 * Interface for axis renderers.
 */
interface AxisRenderer {
    /**
     * Function called when axis needs to be drawn.
     */
    fun DrawScope.render(context: RendererContext)

    /**
     * @return position of this axis.
     */
    fun getNormalisedPosition(): Float
}

interface HorizontalAxisRenderer : AxisRenderer

interface VerticalAxisRenderer : AxisRenderer
