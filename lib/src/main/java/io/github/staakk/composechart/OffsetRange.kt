package io.github.staakk.composechart

import androidx.compose.ui.geometry.Offset


data class OffsetRange(
    val x: ClosedFloatingPointRange<Float>,
    val y: ClosedFloatingPointRange<Float>
)

fun Offset.coerceIn(range: OffsetRange) = Offset(
    x.coerceIn(range.x),
    y.coerceIn(range.y)
)