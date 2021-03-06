package io.github.staakk.cchart.data

import io.github.staakk.cchart.DataLabelScope

data class RenderedPoint(
    val point: Point,
    val seriesName: String,
    /**
     * Horizontal position at which the [point] was rendered.
     */
    val x: Float,
    /**
     * Vertical position at which the [point] was rendered.
     */
    val y: Float,
)

fun RenderedPoint.toDataLabelScope() = DataLabelScope(point, seriesName)