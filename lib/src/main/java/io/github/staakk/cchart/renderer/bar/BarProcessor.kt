package io.github.staakk.cchart.renderer.bar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.renderer.BoundingShape
import io.github.staakk.cchart.renderer.BoundingShapeProvider
import io.github.staakk.cchart.renderer.ChartContext
import io.github.staakk.cchart.renderer.Drawer
import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.PrimitiveStyle
import java.util.SortedMap
import kotlin.math.abs

class BarProcessor(
    private val preferredWidth: Float,
    private val minimalSpacing: Float = 10f,
    private val style: StyleProvider = StyleProvider { _, _ -> PrimitiveStyle() },
    private val sizeTransform: (Size) -> Size = { it }
) : Drawer, BoundingShapeProvider {
    private var lastDrawnShapes: List<BoundingShape> = emptyList()

    // TODO return boolean indicating whether there's more to draw?
    override fun RendererScope.draw(index: Int, rendererPoints: List<RendererPoint<*>>) {
        // Draw everything on first draw call.
        if (index != 0) return

        val groups = rendererPoints.groupByTo(sortedMapOf(), RendererPoint<*>::x)
        val width = getBarWidth(groups, chartContext)
        val drawnShapes = mutableListOf<BoundingShape>()
        groups.forEach { (rendererX, points) ->
            points.forEachIndexed { indexInGroup, point ->
                val unitOffset = -points.size / 2 + indexInGroup
                val halfWidth = width / 2f
                val barPositionInGroup = unitOffset * width
                val oddOffset = (points.size % 2) * halfWidth

                val x = rendererX + barPositionInGroup - oddOffset
                val y = chartContext.toRendererHeight(point.data.y)

                val baseLeft = Offset(x, chartContext.toRendererY(0f))
                val barSize = sizeTransform(Size(width, y))

                with(style(indexInGroup, point)) {
                    drawRect(
                        topLeft = baseLeft.copy(y = baseLeft.y - barSize.height),
                        size = barSize,
                        alpha = alpha,
                        brush = brush,
                        style = style,
                        colorFilter = colorFilter,
                        blendMode = blendMode
                    )
                }

                drawnShapes += BoundingShape.Rect(
                    data = point.data,
                    labelAnchorX = baseLeft.x + barSize.width / 2,
                    labelAnchorY = baseLeft.y - barSize.height,
                    topLeft = baseLeft.copy(y = baseLeft.y - barSize.height),
                    bottomRight = Offset(baseLeft.x + barSize.width, baseLeft.y),
                )
            }
        }
    }

    private fun getBarWidth(
        groups: SortedMap<Float, MutableList<RendererPoint<*>>>,
        context: ChartContext,
    ): Float {
        val minXDistance = context.toRendererWidth(groups.keys.getMinDistance())
        val maxItemsNo = groups.maxOfOrNull { it.value.size } ?: 1
        val pointWidth = maxItemsNo * preferredWidth
        return if (minXDistance - pointWidth < minimalSpacing) {
            (minXDistance - minimalSpacing) / maxItemsNo
        } else {
            preferredWidth
        }
    }

    private fun Set<Float>.getMinDistance() =
        windowed(2) { abs(it[0] - it[1]) }
            .minOrNull()
            ?: Float.MAX_VALUE

    override fun provide(index: Int, rendererPoints: List<RendererPoint<*>>): List<BoundingShape> {
        return if (index != 0) emptyList()
        else lastDrawnShapes
    }
}