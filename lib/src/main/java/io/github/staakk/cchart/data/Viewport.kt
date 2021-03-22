package io.github.staakk.cchart.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Specifies bounds for rendering of the chart. Those values should be represented in the same
 * coordinate system as the data provided for the chart.
 */
data class Viewport(
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float
) {

    constructor(minX: Number, maxX: Number, minY: Number, maxY: Number)
            : this(minX.toFloat(), maxX.toFloat(), minY.toFloat(), maxY.toFloat())

    val size = Size(abs(maxX - minX), abs(maxY - minY))
    val width get() = size.width
    val height get() = size.height

    fun applyZoom(zoom: Float, direction: Offset, minSize: Size, maxSize: Size): Viewport {
        val dx = (width / zoom)
        val dy = (height / zoom)
        val ddx = (width - dx) / 2 * direction.x
        val ddy = (height - dy) / 2 * direction.y
        var minX = minX + ddx
        var maxX = maxX - ddx
        var minY = minY + ddy
        var maxY = maxY - ddy
        val width = abs(maxX - minX)
        val height = abs(maxY - minY)

        if (width < minSize.width) {
            val diff = abs(minSize.width - width) / 2
            minX -= diff
            maxX += diff
        }
        if (height < minSize.height) {
            val diff = abs(minSize.height - height) / 2
            minY -= diff
            maxY += diff
        }

        if (width > maxSize.width) {
            val diff = abs(width - maxSize.width) / 2
            minX += diff
            maxX -= diff
        }
        if (height > maxSize.height) {
            val diff = abs(height - maxSize.height) / 2
            minY += diff
            maxY -= diff
        }
        return Viewport(minX = minX, maxX = maxX, minY = minY, maxY = maxY)
    }

    fun applyPan(panX: Float, panY: Float, maxViewport: Viewport): Viewport {
        var newMinX = minX + panX
        var newMaxX = maxX + panX
        if (newMinX < maxViewport.minX) {
            newMaxX += abs(maxViewport.minX - newMinX)
            newMinX = maxViewport.minX
        }
        if (newMaxX > maxViewport.maxX) {
            newMinX -= abs(maxViewport.maxX - newMaxX)
            newMaxX = maxViewport.maxX
        }

        var newMinY = minY + panY
        var newMaxY = maxY + panY
        if (newMinY < maxViewport.minY) {
            newMaxY += abs(maxViewport.minY - newMinY)
            newMinY = maxViewport.minY
        }
        if (newMaxY > maxViewport.maxY) {
            newMinY -= abs(maxViewport.maxY - newMaxY)
            newMaxY = maxViewport.maxY
        }

        return Viewport(minX = newMinX, maxX = newMaxX, minY = newMinY, maxY = newMaxY)
    }

    fun contains(point: Point) = point.x in minX..maxX && point.y in minY..maxY

    operator fun plus(other: Viewport) = Viewport(
        minX = min(minX, other.minX),
        maxX = max(maxX, other.maxX),
        minY = min(minY, other.minY),
        maxY = max(maxY, other.maxY)
    )

    companion object {
        fun Iterable<Series>.getViewport(): Viewport {
            var maxX = Float.MIN_VALUE
            var minX = Float.MAX_VALUE
            var maxY = Float.MIN_VALUE
            var minY = Float.MAX_VALUE

            forEach { series ->
                series.forEach { point ->
                    if (point.x > maxX) maxX = point.x
                    if (point.x < minX) minX = point.x
                    if (point.y > maxY) maxY = point.y
                    if (point.y < minY) minY = point.y
                }
            }

            return Viewport(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }

        fun Iterable<GroupedSeries>.getViewportFromGroupedSeries(): Viewport {
            var maxX = Float.MIN_VALUE
            var minX = Float.MAX_VALUE
            var maxY = Float.MIN_VALUE
            var minY = Float.MAX_VALUE

            forEach { s ->
                s.forEach { group ->
                    group.forEach { point ->
                        if (point.x > maxX) maxX = point.x
                        if (point.x < minX) minX = point.x
                        if (point.y > maxY) maxY = point.y
                        if (point.y < minY) minY = point.y
                    }
                }
            }

            return Viewport(maxX = maxX, minX = minX, maxY = maxY, minY = minY)
        }
    }
}