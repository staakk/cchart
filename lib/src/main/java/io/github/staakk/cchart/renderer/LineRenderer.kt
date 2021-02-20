package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Series

class LineRenderer(
    private val brush: Brush,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val cap: StrokeCap = StrokeCap.Round,
) : SeriesRenderer {

    override fun DrawScope.render(context: RendererContext, series: List<Series>) {
        series.forEach { s ->
            drawPath(
                path = Path().apply {
                    s.points.windowed(2) {
                        moveTo(
                            context.dataToRendererCoordX(it[0].x),
                            context.dataToRendererCoordY(it[0].y),
                        )
                        lineTo(
                            context.dataToRendererCoordX(it[1].x),
                            context.dataToRendererCoordY(it[1].y),
                        )
                        close()
                    }
                },
                alpha = 1.0f,
                brush = brush,
                style = Stroke(
                    width = strokeWidth,
                    cap = cap
                ),
            )
        }
    }
}