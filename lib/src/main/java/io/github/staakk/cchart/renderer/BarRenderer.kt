package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import io.github.staakk.cchart.data.Data
import io.github.staakk.cchart.data.Viewport
import kotlin.math.abs

fun interface BarDrawer {

    /**
     * Draws the bar.
     *
     * @param index Index of a bar in a group.
     * @param data Data point to render.
     * @param topLeft Top left corner of a bar.
     * @param size Size of a bar.
     */
    fun DrawScope.draw(index: Int, data: Data, topLeft: Offset, size: Size)
}

fun interface BarBoundingShapeProvider {

    /**
     * Provides [BoundingShape] that is used for rendering data labels and on click listener of the
     * [io.github.staakk.cchart.Chart].
     *
     * __Note__: [topLeft] and [size] values correspond to values provided
     * to [BarDrawer] by [barGroupRenderer] and not to what was rendered by it.
     *
     * @param index Index of a bar in a group.
     * @param data Data point to provide [BoundingShape] for.
     * @param topLeft Top left corner of a bar.
     * @param size Size of a bar.
     */
    fun DrawScope.provide(index: Int, data: Data, topLeft: Offset, size: Size): BoundingShape
}

/**
 * Renders bars on the chart.
 *
 * @param preferredWidth Preferred width of the bars. If there's no enough space to maintain
 * [minimalSpacing] distance between the bars this value will be adjusted.
 * @param minimalSpacing Minimal spacing between the bars.
 * @param barDrawer Draws the bars.
 * @param boundingShapeProvider Provides bounding shapes for rendered bars.
 *
 * @see [io.github.staakk.cchart.ChartScope.series]
 */
fun barGroupRenderer(
    preferredWidth: Float,
    minimalSpacing: Float = 10f,
    barDrawer: BarDrawer = drawBar(),
    boundingShapeProvider: BarBoundingShapeProvider = barBoundingShapeProvider()
): GroupedSeriesRenderer = GroupedSeriesRenderer { context, series ->
    val renderedPoints = mutableListOf<BoundingShape>()
    val drawingBounds = getDrawingBounds(context)
    val groups = series.filter {
        if (it.isEmpty()) false
        else {
            val first = it.first()
            first.x in drawingBounds.minX..drawingBounds.maxX
        }
    }
    val width = getBarWidth(groups, context, preferredWidth, minimalSpacing)

    groups.forEach { group ->
        val groupSize = group.size
        group.forEachIndexed { index, point ->
            val unitOffset = -groupSize / 2 + index
            val halfWidth = width / 2f

            val x =
                context.dataToRendererCoordX(point.x) + unitOffset * width + (1 - groupSize % 2) * halfWidth
            val y = context.dataToRendererSizeY(point.y)

            val topLeft = Offset(x - halfWidth, context.dataToRendererCoordY(0f))
            val size = Size(width, -y)
            with(barDrawer) { draw(index, point, topLeft, size) }
            renderedPoints += with(boundingShapeProvider) { provide(index, point, topLeft, size) }
        }
    }
    renderedPoints
}

private fun getBarWidth(
    groups: List<List<Data>>,
    context: RendererContext,
    preferredWidth: Float,
    minimalSpacing: Float
): Float {
    val minXDistance = context.dataToRendererSizeX(groups.getMinXDistance())
    val maxItemsNo = groups.maxByOrNull { it.size }?.size ?: 1
    val pointWidth = maxItemsNo * preferredWidth
    return if (minXDistance - pointWidth < minimalSpacing) {
        (minXDistance - minimalSpacing) / maxItemsNo
    } else {
        preferredWidth
    }
}

private fun List<List<Data>>.getMinXDistance() =
    windowed(2) { abs(it[0][0].x - it[1][0].x) }
        .minOrNull()
        ?: Float.MAX_VALUE

private fun getDrawingBounds(rendererContext: RendererContext): Viewport {
    val canvasBounds = rendererContext.bounds
    val canvasWidth = rendererContext.canvasSize.width
    return Viewport(
        maxX = canvasBounds.maxX + canvasWidth / 2,
        minX = canvasBounds.minX - canvasWidth / 2,
        minY = Float.MIN_VALUE,
        maxY = Float.MAX_VALUE
    )
}

fun drawBar(
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    brushProvider: (index: Int, Data) -> Brush = { _, _ -> SolidColor(Color.Black) }
) = BarDrawer { index, point, topLeft, size ->
    drawRect(
        brush = brushProvider(index, point),
        topLeft = topLeft,
        size = size,
        style = style,
        alpha = alpha,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
}

fun barBoundingShapeProvider() = BarBoundingShapeProvider { _, point, topLeft, size ->
    BoundingShape.Rect(
        data = point,
        labelAnchorX = topLeft.x + size.width / 2,
        labelAnchorY = size.height,
        topLeft = topLeft,
        bottomRight = Offset(topLeft.x + size.width, topLeft.y - size.height)
    )
}