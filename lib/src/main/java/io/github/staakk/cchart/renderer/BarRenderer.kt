package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Series

class BarRenderer(
    private val brush: Brush,
    private val barWidth: Float
) : SeriesRenderer {
    override fun DrawScope.render(context: RendererContext, series: Series) {
        series.points.forEach {
            drawRect(
                brush = brush,
                topLeft = Offset(
                    context.dataToRendererCoordX(it.x) - barWidth / 2f,
                    context.dataToRendererCoordY(0f)
                ),
                size = Size(barWidth, context.dataToRendererSizeY(it.y))
            )
        }
    }
}