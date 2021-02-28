package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.renderer.RendererContext
import io.github.staakk.cchart.util.lineHeight

private class HorizontalLabelRendererImpl(
    private val paint: Paint,
    private val location: HorizontalLabelLocation,
    private val side: HorizontalLabelSide,
    private val axisDistance: Float,
    private val labelsProvider: LabelsProvider,
) : HorizontalLabelRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.provide(context.bounds.minX, context.bounds.maxX)
                .forEach { (text, offset) ->
                    val textWidth = paint.measureText(text)
                    val x = context.dataToRendererCoordX(offset) - textWidth / 2
                    if (x > 0 && x + textWidth < size.width) {
                        val lines = text.lines()
                        lines.forEachIndexed { index, line ->
                            canvas.nativeCanvas.drawText(
                                line,
                                context.dataToRendererCoordX(offset) - paint.measureText(line) / 2,
                                getYPosition(this, index, lines.count()),
                                paint
                            )
                        }
                    }
                }
        }
    }

    override fun getMaxLabelSize(): Size = labelsProvider.getMaxLabelMaxSize(paint).let {
        it.copy(height = it.height + axisDistance)
    }

    private fun getYPosition(drawScope: DrawScope, lineNo: Int, linesCount: Int): Float {
        val position = getNormalisedPosition() * drawScope.size.height
        val textHeight = paint.fontMetrics.lineHeight
        return position + when (side) {
            HorizontalLabelSide.BELOW -> textHeight + axisDistance + lineNo * paint.fontMetrics.lineHeight
            HorizontalLabelSide.ABOVE -> -axisDistance - (linesCount - lineNo - 1) * paint.fontMetrics.lineHeight
        }
    }

    override fun getNormalisedPosition() = when (location) {
        HorizontalLabelLocation.BOTTOM -> 1f
        HorizontalLabelLocation.TOP -> 0f
    }
}

enum class HorizontalLabelLocation { BOTTOM, TOP }

enum class HorizontalLabelSide { BELOW, ABOVE }

@Composable
fun horizontalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: HorizontalLabelLocation = HorizontalLabelLocation.BOTTOM,
    side: HorizontalLabelSide = HorizontalLabelSide.BELOW,
    axisDistance: Float = 12f,
    labelsProvider: LabelsProvider = IntLabelsProvider,
): HorizontalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return HorizontalLabelRendererImpl(paint, location, side, axisDistance, labelsProvider)
}

fun horizontalLabelRenderer(
    paint: Paint,
    location: HorizontalLabelLocation = HorizontalLabelLocation.BOTTOM,
    side: HorizontalLabelSide = HorizontalLabelSide.BELOW,
    axisDistance: Float = 12f,
    labelsProvider: LabelsProvider = IntLabelsProvider
): HorizontalLabelRenderer =
    HorizontalLabelRendererImpl(paint, location, side, axisDistance, labelsProvider)