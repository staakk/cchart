package io.github.staakk.cchart.util

import androidx.compose.foundation.gestures.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.util.fastAny
import androidx.compose.ui.util.fastForEach
import kotlin.math.abs

/**
 * Adapted from [androidx.compose.foundation.gestures.detectTransformGestures].
 * Provides zooming direction as positive unit vector instead of rotation.
 */
suspend fun PointerInputScope.detectTransformGestures(
    onGesture: (centroid: Offset, pan: Offset, zoom: Float, direction: Offset) -> Unit
) {
    forEachGesture {
        awaitPointerEventScope {
            var zoom = 1f
            var pan = Offset.Zero
            var pastTouchSlop = false
            val touchSlop = viewConfiguration.touchSlop

            awaitFirstDown(requireUnconsumed = false)
            do {
                val event = awaitPointerEvent()
                val canceled = event.changes.fastAny { it.anyPositionChangeConsumed() }
                if (!canceled) {
                    val zoomChange = event.calculateZoom()
                    val panChange = event.calculatePan()

                    if (!pastTouchSlop) {
                        zoom *= zoomChange
                        pan += panChange

                        val centroidSize = event.calculateCentroidSize(useCurrent = false)
                        val zoomMotion = abs(1 - zoom) * centroidSize
                        val panMotion = pan.getDistance()

                        if (zoomMotion > touchSlop ||
                            panMotion > touchSlop
                        ) {
                            pastTouchSlop = true
                        }
                    }

                    if (pastTouchSlop) {
                        val centroid = event.calculateCentroid(useCurrent = false)
                        val direction = event.calculateDirection()
                        if (zoomChange != 1f ||
                            panChange != Offset.Zero
                        ) {
                            onGesture(centroid, panChange, zoomChange, direction)
                        }
                        event.changes.fastForEach {
                            if (it.positionChanged()) {
                                it.consumeAllChanges()
                            }
                        }
                    }
                }
            } while (!canceled && event.changes.fastAny { it.pressed })
        }
    }
}

private fun PointerEvent.calculateDirection(): Offset {
    if (changes.size < 2) return Offset(0f, 0f)
    val first = changes.first().position
    var direction = Offset(0f, 0f)
    (1 until changes.size).asSequence()
        .map { changes[it] }
        .forEach { change ->
            if (change.pressed && change.previousPressed) {
                val position = change.position
                direction += position - first
            }
        }
    val length = direction.getDistance()
    return Offset(abs(direction.x / length), abs(direction.y / length))
}
