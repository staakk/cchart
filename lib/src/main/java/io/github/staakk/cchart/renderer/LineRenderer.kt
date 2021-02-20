package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Series

class LineRenderer(
    private val brush: Brush,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
) : SeriesRenderer {

    override fun DrawScope.render(context: RendererContext, series: Series) {
        series.points.windowed(2) {
            drawLine(
                brush = brush,
                strokeWidth = strokeWidth,
                pathEffect = pathEffect,
                cap = cap,
                start = Offset(
                    context.dataToRendererCoordX(it[0].x),
                    context.dataToRendererCoordY((it[0].y))
                ),
                end = Offset(
                    context.dataToRendererCoordX(it[1].x),
                    context.dataToRendererCoordY(it[1].y),
                ),
            )
        }
    }
}