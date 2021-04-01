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
     * @param baseLeft Coordinate of the left corner at the base of the bar.
     * @param size Size of a bar.
     */
    fun RendererScope.draw(index: Int, data: Data<*>, baseLeft: Offset, size: Size)
}

fun interface BarBoundingShapeProvider {

    /**
     * Provides [BoundingShape] that is used for rendering data labels and on click listener of the
     * [io.github.staakk.cchart.Chart].
     *
     * __Note__: [baseLeft] and [size] values are the same as passed to the [BarDrawer].
     *
     * @param index Index of a bar in a group.
     * @param data Data point to provide [BoundingShape] for.
     * @param baseLeft Coordinate of the left corner at the base of the bar.
     * @param size Size of a bar.
     */
    fun RendererScope.provide(index: Int, data: Data<*>, baseLeft: Offset, size: Size): BoundingShape
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
    barDrawer: BarDrawer = barDrawer(),
    boundingShapeProvider: BarBoundingShapeProvider = barBoundingShapeProvider()
): GroupedSeriesRenderer = GroupedSeriesRenderer { series ->
    val renderedPoints = mutableListOf<BoundingShape>()
    val drawingBounds = getDrawingBounds(chartContext)
    val groups = series.filter {
        if (it.isEmpty()) false
        else {
            val first = it.first()
            first.x in drawingBounds.minX..drawingBounds.maxX
        }
    }
    val width = getBarWidth(groups, chartContext, preferredWidth, minimalSpacing)

    groups.forEach { group ->
        val groupSize = group.size
        group.forEachIndexed { index, point : Data<*> ->
            val unitOffset = -groupSize / 2 + index
            val halfWidth = width / 2f

            val x =
                chartContext.toRendererX(point.x) + unitOffset * width - (groupSize % 2) * halfWidth
            val y = chartContext.toRendererHeight(point.y)

            val baseLeft = Offset(x, chartContext.toRendererY(0f))
            val size = Size(width, y)
            with(barDrawer) { draw(index, point, baseLeft, size) }
            renderedPoints += with(boundingShapeProvider) {
                provide(index, point, baseLeft, size)
            }
        }
    }
    renderedPoints
}

private fun getBarWidth(
    groups: List<List<Data<*>>>,
    context: ChartContext,
    preferredWidth: Float,
    minimalSpacing: Float
): Float {
    val minXDistance = context.toRendererWidth(groups.getMinXDistance())
    val maxItemsNo = groups.maxByOrNull { it.size }?.size ?: 1
    val pointWidth = maxItemsNo * preferredWidth
    return if (minXDistance - pointWidth < minimalSpacing) {
        (minXDistance - minimalSpacing) / maxItemsNo
    } else {
        preferredWidth
    }
}

private fun List<List<Data<*>>>.getMinXDistance() =
    windowed(2) { abs(it[0][0].x - it[1][0].x) }
        .minOrNull()
        ?: Float.MAX_VALUE

private fun getDrawingBounds(chartContext: ChartContext): Viewport {
    val canvasBounds = chartContext.viewport
    val canvasWidth = chartContext.canvasSize.width
    return Viewport(
        maxX = canvasBounds.maxX + canvasWidth / 2,
        minX = canvasBounds.minX - canvasWidth / 2,
        minY = Float.MIN_VALUE,
        maxY = Float.MAX_VALUE
    )
}

fun barDrawer(
    style: DrawStyle = Fill,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    blendMode: BlendMode = DrawScope.DefaultBlendMode,
    brushProvider: (index: Int, Data<*>) -> Brush = { _, _ -> SolidColor(Color.Black) }
) = BarDrawer { index, point, baseLeft, size ->
    drawRect(
        brush = brushProvider(index, point),
        topLeft = baseLeft.copy(y = baseLeft.y - size.height),
        size = size,
        style = style,
        alpha = alpha,
        colorFilter = colorFilter,
        blendMode = blendMode
    )
}

fun barBoundingShapeProvider() = BarBoundingShapeProvider { _, point, baseLeft, size ->
    BoundingShape.Rect(
        data = point,
        labelAnchorX = baseLeft.x + size.width / 2,
        labelAnchorY = baseLeft.y - size.height,
        topLeft = baseLeft.copy(y = baseLeft.y - size.height),
        bottomRight = Offset(baseLeft.x + size.width, baseLeft.y)
    )
}