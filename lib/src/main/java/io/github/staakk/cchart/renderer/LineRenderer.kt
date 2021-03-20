package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
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
        series: Series
    ): List<RenderedShape> {
        val renderedPoints = mutableListOf<RenderedShape>()
        if (series.points.size < 2) return renderedPoints
        val pointsToRender = series.getLineInViewport(context.bounds)
        renderedPoints += RenderedShape.Circle(
            seriesName = series.name,
            point = pointsToRender[0],
            labelAnchorX = context.dataToRendererCoordX(pointsToRender[0].x),
            labelAnchorY = context.dataToRendererCoordY(pointsToRender[0].y),
            center = Offset(
                x = context.dataToRendererCoordX(pointsToRender[0].x),
                y = context.dataToRendererCoordY(pointsToRender[0].y),
            ),
            radius = 20f
        )
        drawPath(
            path = Path().apply {
                pointsToRender.windowed(2) {
                    moveTo(
                        context.dataToRendererCoordX(it[0].x),
                        context.dataToRendererCoordY(it[0].y),
                    )
                    val secondPointX = context.dataToRendererCoordX(it[1].x)
                    val secondPointY = context.dataToRendererCoordY(it[1].y)
                    lineTo(secondPointX, secondPointY)
                    renderedPoints += RenderedShape.Circle(
                        seriesName = series.name,
                        point = pointsToRender[0],
                        labelAnchorX = secondPointX,
                        labelAnchorY = secondPointY,
                        center = Offset(
                            x = secondPointX,
                            y = secondPointY,
                        ),
                        radius = 20f
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