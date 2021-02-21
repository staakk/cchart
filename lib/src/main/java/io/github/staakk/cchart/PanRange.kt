package io.github.staakk.cchart

import androidx.compose.ui.geometry.Offset

/**
 * Allows to provide panning boundaries for [Chart].
 *
 * @param x Horizontal pan range in pixels.
 * @param y Vertical pan range in pixels.
 */
data class PanRange(
    val x: ClosedFloatingPointRange<Float>,
    val y: ClosedFloatingPointRange<Float>
) {

    companion object {
        private val None = 0f..0f

        /**
         * No panning.
         */
        val NoPan = PanRange(None, None)

        /**
         * Unrestricted panning.
         */
        val Unrestricted =
            PanRange(Float.MIN_VALUE..Float.MAX_VALUE, Float.MIN_VALUE..Float.MAX_VALUE)

        /**
         * Allows panning the [Chart] in horizontal direction.
         */
        fun horizontal(range: ClosedFloatingPointRange<Float>) = PanRange(range, None)

        /**
         * Allows panning the [Chart] in vertical direction.
         */
        fun vertical(range: ClosedFloatingPointRange<Float>) = PanRange(None, range)
    }
}

internal fun Offset.coerceIn(range: PanRange) = Offset(
    x.coerceIn(range.x),
    y.coerceIn(range.y)
)