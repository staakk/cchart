package io.github.staakk.cchart.bounds

import androidx.compose.ui.geometry.Offset
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.pointOf
import kotlin.math.pow

sealed class Bounds {

    abstract val data: Data<*>

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
        override val data: Data<*>,
        override val labelAnchorX: Float,
        override val labelAnchorY: Float,
        val center: Offset,
        val radius: Float
    ) : Bounds() {

        override fun contains(offset: Offset): Boolean =
            radius.pow(2) - (center.x - offset.x).pow(2) - (center.y - offset.y).pow(2) >= 0f
    }

    data class Rect(
        override val data: Data<*>,
        override val labelAnchorX: Float,
        override val labelAnchorY: Float,
        val topLeft: Offset,
        val bottomRight: Offset
    ) : Bounds() {

        override fun contains(offset: Offset): Boolean =
            topLeft.x <= offset.x && offset.x <= bottomRight.x &&
                    bottomRight.y <= offset.y && offset.y <= topLeft.y
    }

    object None: Bounds() {
        override val data: Data<*> = pointOf(0, 0)
        override val labelAnchorX: Float = 0f
        override val labelAnchorY: Float = 0f
        override fun contains(offset: Offset): Boolean = false
    }
}