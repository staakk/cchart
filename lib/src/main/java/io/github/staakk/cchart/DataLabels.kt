package io.github.staakk.cchart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.RenderedPoint
import io.github.staakk.cchart.data.toDataLabelScope

@Composable
fun DataLabels(
    modifier: Modifier,
    renderedPoints: List<RenderedPoint>,
    canvasSize: Size,
    horizontalAlignment: HorizontalAlignment,
    verticalAlignment: VerticalAlignment,
    labelContent: @Composable DataLabelScope.() -> Unit,
) {
    val density = LocalDensity.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        renderedPoints.forEach { renderedPoint ->
            Box(modifier = Modifier.absoluteOffsetToCenter(
                with(density) { renderedPoint.x.toDp() },
                with(density) { canvasSize.height.toDp() + renderedPoint.y.toDp() },
                horizontalAlignment,
                verticalAlignment
            )) {
                labelContent.invoke(renderedPoint.toDataLabelScope())
            }
        }
    }
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

data class DataLabelScope(
    val point: Point,
    val seriesName: String
)


private class OffsetToCenterModifier(
    val x: Dp,
    val y: Dp,
    val rtlAware: Boolean,
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
                y == otherModifier.y &&
                rtlAware == otherModifier.rtlAware
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + rtlAware.hashCode()
        return result
    }

    override fun toString(): String = "OffsetToCenterModifier(x=$x, y=$y, rtlAware=$rtlAware)"
}

fun Modifier.absoluteOffsetToCenter(
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
        rtlAware = false,
        inspectorInfo = debugInspectorInfo {
            name = "absoluteOffset"
            properties["x"] = x
            properties["y"] = y
        }
    )
)