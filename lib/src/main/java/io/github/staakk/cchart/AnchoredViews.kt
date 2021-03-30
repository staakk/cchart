package io.github.staakk.cchart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.renderer.BoundingShape

@Composable
internal fun DataLabels(
    modifier: Modifier,
    renderedShapes: List<BoundingShape>,
    canvasSize: Size,
    labelContent: @Composable AnchorScope.() -> Unit,
) {
    AnchoredViews(
        modifier = modifier,
        canvasSize = canvasSize,
        anchors = renderedShapes
            .map { it.data to Offset(it.labelAnchorX, it.labelAnchorY) to labelContent }
            .toMap()
    )
}

@Composable
internal fun AnchoredViews(
    modifier: Modifier,
    canvasSize: Size,
    anchors: Map<Pair<Data, Offset>, @Composable AnchorScope.() -> Unit>
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
    val data: Data,
    val offset: Offset,
    val canvasSize: Size,
    val density: Density
) {

    fun Modifier.align(
        horizontalAlignment: HorizontalAlignment,
        verticalAlignment: VerticalAlignment
    ): Modifier = absoluteOffsetToCenter(
        with(density) { offset.x.toDp() },
        with(density) { offset.y.toDp() },
        horizontalAlignment,
        verticalAlignment
    )
}

enum class HorizontalAlignment {
    LEFT {
        override fun calculatePosition(position: Int, placeable: Placeable): Int =
            position - placeable.width
    },
    CENTER {
        override fun calculatePosition(position: Int, placeable: Placeable): Int =
            position - placeable.width / 2

    },
    RIGHT {
        override fun calculatePosition(position: Int, placeable: Placeable): Int =
            position
    };

    internal abstract fun calculatePosition(position: Int, placeable: Placeable): Int
}

enum class VerticalAlignment {
    TOP {
        override fun calculatePosition(position: Int, placeable: Placeable): Int =
            position - placeable.height
    },
    CENTER {
        override fun calculatePosition(position: Int, placeable: Placeable): Int =
            position - placeable.height / 2
    },
    BOTTOM {
        override fun calculatePosition(position: Int, placeable: Placeable): Int =
            position
    };

    internal abstract fun calculatePosition(position: Int, placeable: Placeable): Int
}

private class OffsetToCenterModifier(
    val x: Dp,
    val y: Dp,
    val horizontalAlignment: HorizontalAlignment,
    val verticalAlignment: VerticalAlignment,
    inspectorInfo: InspectorInfo.() -> Unit
) : LayoutModifier, InspectorValueInfo(inspectorInfo) {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(placeable.width, placeable.height) {
            placeable.place(
                horizontalAlignment.calculatePosition(x.roundToPx(), placeable),
                verticalAlignment.calculatePosition(y.roundToPx(), placeable)
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
    horizontalAlignment: HorizontalAlignment,
    verticalAlignment: VerticalAlignment
) = this.then(
    OffsetToCenterModifier(
        x = x,
        y = y,
        horizontalAlignment = horizontalAlignment,
        verticalAlignment = verticalAlignment,
        inspectorInfo = debugInspectorInfo {
            name = "absoluteOffset"
            properties["x"] = x
            properties["y"] = y
        }
    )
)