package io.github.staakk.cchart.style

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke

fun primitiveStyle(builder: PrimitiveStyle.() -> Unit): PrimitiveStyle = PrimitiveStyle().apply(builder)

class PrimitiveStyle internal constructor() {
    var alpha: Float = 1.0f
    var brush: Brush = SolidColor(Color.Black)
    var style: DrawStyle = Stroke(width = 5f, cap = StrokeCap.Square)
    var colorFilter: ColorFilter? = null
    var blendMode: BlendMode = DrawScope.DefaultBlendMode
}