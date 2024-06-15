package io.github.staakk.cchart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.bounds.Bounds

@Composable
internal fun DataLabels(
    modifier: Modifier,
    renderedShapes: List<Bounds>,
    canvasSize: Size,
    labelContent: @Composable AnchorScope.() -> Unit,
) {
    AnchoredViews(
        modifier = modifier,
        canvasSize = canvasSize,
        anchors = renderedShapes
            .associate { it.point to Offset(it.labelAnchorX, it.labelAnchorY) to labelContent }
    )
}

@Composable
internal fun AnchoredViews(
    modifier: Modifier,
    canvasSize: Size,
    anchors: Map<Pair<Point<*>, Offset>, @Composable AnchorScope.() -> Unit>
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        anchors.forEach { (pointWithOffset, content) ->
            content(AnchorScope(pointWithOffset.first, pointWithOffset.second, canvasSize, density))
        }
    }
}

data class AnchorScope(
    val point: Point<*>,
    val offset: Offset,
    val canvasSize: Size,
    val density: Density
) {

    fun Modifier.align(alignment: Alignment): Modifier = absoluteOffsetToCenter(
        with(density) { offset.x.toDp() },
        with(density) { offset.y.toDp() },
        alignment = alignment,
    )
}

private class OffsetToCenterModifier(
    val x: Dp,
    val y: Dp,
    val alignment: Alignment,
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            val result = alignment.align(
                IntSize(placeable.width, placeable.height),
                IntSize.Zero,
                LayoutDirection.Ltr
            )
            placeable.place(
                x = x.roundToPx() + result.x,
                y = y.roundToPx() + result.y,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherModifier = other as? OffsetToCenterModifier ?: return false

        return x == otherModifier.x &&
                y == otherModifier.y
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

    override fun toString(): String = "OffsetToCenterModifier(x=$x, y=$y)"
}

internal fun Modifier.absoluteOffsetToCenter(
    x: Dp,
    y: Dp,
    alignment: Alignment,
) = this.then(
    OffsetToCenterModifier(
        x = x,
        y = y,
        alignment = alignment,
        inspectorInfo = debugInspectorInfo {
            name = "absoluteOffset"
            properties["x"] = x
            properties["y"] = y
        }
    )
)