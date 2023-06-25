package io.github.staakk.cchart.dsl

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

class LineStyle internal constructor() {
    var alpha: Float = 1.0f
    var brush: Brush = SolidColor(Color.Black)
    val strokeWidth: Float = Stroke.HairlineWidth
    val pathEffect: PathEffect? = null
    val cap: StrokeCap = Stroke.DefaultCap
    var colorFilter: ColorFilter? = null
    var blendMode: BlendMode = DrawScope.DefaultBlendMode
}