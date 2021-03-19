package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.DataLabelScope
import io.github.staakk.cchart.data.Point
import kotlin.math.pow

sealed class RenderedShape {

    abstract val point: Point

    abstract val seriesName: String

    /**
     * Horizontal position at which label should be anchored.
     */
    abstract val labelAnchorX: Float

    /**
     * Vertical position at which label should be anchored.
     */
    abstract val labelAnchorY: Float

    /**
     * @return `true` if [offset] is inside the shape.
     */
    abstract fun contains(offset: Offset): Boolean

    data class Circle(
        override val point: Point,
        override val seriesName: String,
        override val labelAnchorX: Float,
        override val labelAnchorY: Float,
        val center: Offset,
        val radius: Float
    ) : RenderedShape() {

        override fun contains(offset: Offset): Boolean =
            radius.pow(2) - (center.x - offset.x).pow(2) - (center.y - offset.y).pow(2) >= 0f
    }

    data class Rect(
        override val point: Point,
        override val seriesName: String,
        override val labelAnchorX: Float,
        override val labelAnchorY: Float,
        val topLeft: Offset,
        val bottomRight: Offset
    ) : RenderedShape() {

        override fun contains(offset: Offset): Boolean =
            topLeft.x <= offset.x && offset.x <= bottomRight.x &&
                    bottomRight.y <= offset.y && offset.y <= topLeft.y
    }
}

fun RenderedShape.toDataLabelScope() = DataLabelScope(point, seriesName)