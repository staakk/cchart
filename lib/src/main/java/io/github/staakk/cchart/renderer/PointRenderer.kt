package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Series

private class PointRenderer(
    private val brush: Brush,
    private val radius: Float,
    private val alpha: Float,
    private val style: DrawStyle,
    private val colorFilter: ColorFilter?,
    private val blendMode: BlendMode,
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
                    ),
                    alpha = alpha,
                    style = style,
                    colorFilter = colorFilter,
                    blendMode = blendMode,
                )
            }
        }
    }
}

fun pointRenderer(
    brush: Brush = SolidColor(Color.Black),
    radius: Float = 15f,
    alpha: Float = 1.0f,
    style: DrawStyle = Fill,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
): SeriesRenderer = PointRenderer(brush, radius, alpha, style, colorFilter, blendMode)