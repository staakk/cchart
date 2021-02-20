package io.github.staakk.cchart

import androidx.compose.ui.geometry.Offset


data class PanRange(
    val x: ClosedFloatingPointRange<Float>,
    val y: ClosedFloatingPointRange<Float>
) {

    companion object {
        private val ZeroRange = 0f..0f

        val NoPan = PanRange(ZeroRange, ZeroRange)

        val Unrestricted =
            PanRange(Float.MIN_VALUE..Float.MAX_VALUE, Float.MIN_VALUE..Float.MAX_VALUE)

        fun horizontal(range: ClosedFloatingPointRange<Float>) = PanRange(range, ZeroRange)

        fun vertical(range: ClosedFloatingPointRange<Float>) = PanRange(ZeroRange, range)
    }
}

fun Offset.coerceIn(range: PanRange) = Offset(
    x.coerceIn(range.x),
    y.coerceIn(range.y)
)