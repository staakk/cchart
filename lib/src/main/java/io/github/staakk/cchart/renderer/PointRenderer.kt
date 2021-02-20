package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Series

class PointRenderer(
    private val brush: Brush,
    private val radius: Float,
) : SeriesRenderer {

    override fun DrawScope.render(context: RendererContext, series: List<Series>) {
        series.forEach { s ->
            s.points.forEach { point ->
                drawCircle(
                    brush = brush,
                    radius = radius,
                    center = Offset(
                        context.dataToRendererCoordX(point.x),
                        context.dataToRendererCoordY(point.y)
                    )
                )
            }
        }
    }
}