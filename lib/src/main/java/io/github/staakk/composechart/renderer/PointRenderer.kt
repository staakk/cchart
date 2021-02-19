package io.github.staakk.composechart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.composechart.data.Series

class PointRenderer(
    private val brush: Brush,
    private val radius: Float,
): SeriesRenderer {

    override fun DrawScope.render(rendererContext: RendererContext, series: Series) {
        series.points.forEach { point ->
            drawCircle(
                brush = brush,
                radius = radius,
                center = Offset(
                    rendererContext.dataToRendererCoordX(point.x.toFloat()),
                    rendererContext.dataToRendererCoordY(point.y.toFloat())
                )
            )
        }
    }
}