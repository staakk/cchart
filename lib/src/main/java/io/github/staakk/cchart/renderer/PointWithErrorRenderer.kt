package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.style.PrimitiveStyle

fun circleWithError(
    brush: Brush = SolidColor(Color.Black),
    alpha: Float = 1.0f,
    strokeWidth: Float = Stroke.HairlineWidth,
    cap: StrokeCap = Stroke.DefaultCap,
    colorFilter: ColorFilter? = null,
    pathEffect: PathEffect? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    circleDrawer: PointDrawer = circleDrawer(PrimitiveStyle())
) = PointDrawer { chartData, rendererData, size ->
    if (rendererData is Data.PointWithError) {
        val center = rendererData.toOffset()
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
    with(circleDrawer) { draw(chartData, rendererData, size) }
}