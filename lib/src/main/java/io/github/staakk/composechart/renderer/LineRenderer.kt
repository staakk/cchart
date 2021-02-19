package io.github.staakk.composechart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import io.github.staakk.composechart.data.Series

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
                    context.dataToRendererCoordX(it[0].x.toFloat()),
                    context.dataToRendererCoordY((it[0].y.toFloat()))
                ),
                end = Offset(
                    context.dataToRendererCoordX(it[1].x.toFloat()),
                    context.dataToRendererCoordY(it[1].y.toFloat()),
                ),
            )
        }
    }
}