package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Series

private class LineRenderer(
    private val brush: Brush,
    private val style: DrawStyle,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
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
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
        }
    }
}

fun lineRenderer(
    brush: Brush = SolidColor(Color.Black),
    style: DrawStyle = Stroke(width = 10f),
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): SeriesRenderer = LineRenderer(brush, style, colorFilter, blendMode)