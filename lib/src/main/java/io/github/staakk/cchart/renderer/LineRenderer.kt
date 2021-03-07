package io.github.staakk.cchart.renderer

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.RenderedPoint
import io.github.staakk.cchart.data.Series

private class LineRenderer(
    private val brush: Brush,
    private val style: DrawStyle,
    private val colorFilter: ColorFilter?,
    private val alpha: Float,
    private val blendMode: BlendMode,
) : SeriesRenderer {

    override fun DrawScope.render(
        context: RendererContext,
        series: List<Series>
    ): List<RenderedPoint> {
        val renderedPoints = mutableListOf<RenderedPoint>()
        series.forEach { s ->
            if (s.points.size < 2) return@forEach

            renderedPoints += RenderedPoint(
                seriesName = s.name,
                point = s.points[0],
                x = context.dataToRendererCoordX(s.points[0].x),
                y = context.dataToRendererCoordY(s.points[0].y),
            )
            drawPath(
                path = Path().apply {
                    s.points.windowed(2) {
                        moveTo(
                            context.dataToRendererCoordX(it[0].x),
                            context.dataToRendererCoordY(it[0].y),
                        )
                        val secondPointX = context.dataToRendererCoordX(it[1].x)
                        val secondPointY = context.dataToRendererCoordY(it[1].y)
                        lineTo(secondPointX, secondPointY)
                        renderedPoints += RenderedPoint(
                            seriesName = s.name,
                            point = s.points[0],
                            x = secondPointX,
                            y = secondPointY,
                        )
                    }
                    close()
                },
                alpha = alpha,
                brush = brush,
                style = style,
                colorFilter = colorFilter,
                blendMode = blendMode
            )
        }
        return renderedPoints
    }
}

fun lineRenderer(
    brush: Brush = SolidColor(Color.Black),
    style: DrawStyle = Stroke(width = 5f, cap = StrokeCap.Round),
    colorFilter: ColorFilter? = null,
    alpha: Float = 1.0f,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): SeriesRenderer = LineRenderer(brush, style, colorFilter, alpha, blendMode)