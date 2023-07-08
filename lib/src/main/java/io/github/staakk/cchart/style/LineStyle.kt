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

fun lineStyle(builder: LineStyle.() -> Unit): LineStyle = LineStyle().apply(builder)

class LineStyle constructor(
    var alpha: Float = 1.0f,
    var brush: Brush = SolidColor(Color.Black),
    var strokeWidth: Float = Stroke.HairlineWidth,
    var pathEffect: PathEffect? = null,
    var cap: StrokeCap = Stroke.DefaultCap,
    var colorFilter: ColorFilter? = null,
    var blendMode: BlendMode = DrawScope.DefaultBlendMode,
)