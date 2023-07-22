package io.github.staakk.cchart.style

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

data class LabelStyle(
    val brush: Brush = SolidColor(Color.Black),
    val textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    val alignment: Alignment = Alignment.TopCenter,
    val labelOffset: Offset = Offset(0f, 12f),
    val clip: Boolean = false,
)