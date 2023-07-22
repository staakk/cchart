package io.github.staakk.cchart.bar

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.clipRect
import io.github.staakk.cchart.ChartContext
import io.github.staakk.cchart.bounds.Bounds
import io.github.staakk.cchart.bounds.BoundsProvider
import io.github.staakk.cchart.renderer.PointsRenderer
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
) : PointsRenderer, BoundsProvider {
    private var lastDrawnShapes: List<Bounds> = emptyList()

    override fun RendererScope.draw(rendererPoints: List<RendererPoint<*>>) = clipRect {
        val groups = rendererPoints.groupByTo(sortedMapOf(), RendererPoint<*>::x)
        val width = getBarWidth(groups, this@draw.chartContext)
        val drawnShapes = mutableListOf<Bounds>()
        groups.forEach { (rendererX, points) ->
            points.forEachIndexed { indexInGroup, point ->
                val unitOffset = -points.size / 2 + indexInGroup
                val halfWidth = width / 2f
                val barPositionInGroup = unitOffset * width
                val oddOffset = (points.size % 2) * halfWidth

                val x = rendererX + barPositionInGroup - oddOffset
                val y = this@draw.chartContext.toRendererHeight(point.point.y)

                val baseLeft = Offset(x, this@draw.chartContext.toRendererY(0f))
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

                drawnShapes += Bounds.Rect(
                    point = point.point,
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

    override fun provide(rendererPoints: List<RendererPoint<*>>) = lastDrawnShapes
}