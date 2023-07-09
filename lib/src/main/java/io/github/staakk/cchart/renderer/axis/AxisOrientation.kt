package io.github.staakk.cchart.renderer.axis

data class AxisOrientation(val x: Float, val y: Float) {
    companion object {
        val Horizontal = AxisOrientation(1f, 0f)
        val Vertical = AxisOrientation(0f, 1f)
    }
}