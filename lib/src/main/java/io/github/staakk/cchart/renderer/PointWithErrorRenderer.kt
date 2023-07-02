package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.style.LineStyle
import io.github.staakk.cchart.style.PrimitiveStyle

fun circleWithError(
    brush: Brush = SolidColor(Color.Black),
    lineStyle: LineStyle = LineStyle(),
    circleDrawer: PointDrawer = circleDrawer(PrimitiveStyle())
) = PointDrawer { chartData, rendererData, size ->
    if (rendererData is Data.PointWithError) {
        val center = rendererData.toOffset()
        with(lineStyle) {
            drawLine(
                start = Offset(center.x, center.y + rendererData.errorY),
                end = Offset(center.x, center.y - rendererData.errorY),
                brush = brush,
                alpha = alpha,
                colorFilter = colorFilter,
                blendMode = blendMode,
                strokeWidth = strokeWidth,
                cap = cap,
                pathEffect = pathEffect
            )
            drawLine(
                start = Offset(center.x + rendererData.errorX, center.y),
                end = Offset(center.x - rendererData.errorX, center.y),
                brush = brush,
                alpha = alpha,
                colorFilter = colorFilter,
                blendMode = blendMode,
                strokeWidth = strokeWidth,
                cap = cap,
                pathEffect = pathEffect
            )
        }
    }
    with(circleDrawer) { draw(chartData, rendererData, size) }
}