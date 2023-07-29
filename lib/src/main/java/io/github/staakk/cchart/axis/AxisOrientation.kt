package io.github.staakk.cchart.axis

/**
 * Represents axis orientation as a vector.
 *
 * @param x
 * @param y The y is always inverted.
 */
data class AxisOrientation(val x: Float, val y: Float) {
    companion object {
        val Horizontal = AxisOrientation(1f, 0f)
        val Vertical = AxisOrientation(0f, 1f)
    }
}