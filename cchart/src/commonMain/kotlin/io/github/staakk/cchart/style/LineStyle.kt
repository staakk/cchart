package io.github.staakk.cchart.style

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

data class LineStyle(
    val alpha: Float = 1.0f,
    val brush: Brush = SolidColor(Color.Black),
    val strokeWidth: Float = Stroke.HairlineWidth,
    val pathEffect: PathEffect? = null,
    val cap: StrokeCap = Stroke.DefaultCap,
    val colorFilter: ColorFilter? = null,
    val blendMode: BlendMode = DrawScope.DefaultBlendMode,
)