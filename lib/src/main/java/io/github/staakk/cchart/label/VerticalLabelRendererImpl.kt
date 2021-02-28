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
import io.github.staakk.cchart.util.countLines
import io.github.staakk.cchart.util.lineHeight

private class VerticalLabelRendererImpl(
    private val paint: Paint,
    private val location: VerticalLabelLocation,
    private val side: VerticalLabelSide,
    private val axisDistance: Float,
    private val labelsProvider: LabelsProvider,
) : VerticalLabelRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.provide(context.bounds.minY, context.bounds.maxY)
                .forEach { (text, offset) ->
                    val textHeight = paint.fontMetrics.lineHeight * (text.countLines() - 1)
                    val y = size.height + context.dataToRendererCoordY(offset) - textHeight / 2
                    val lines = text.lines()
                    lines.forEachIndexed { index, line ->
                        if (y - textHeight > 0f && y + textHeight / 2 < size.height) {
                            canvas.nativeCanvas.drawText(
                                line,
                                getXPosition(this, paint.measureText(text)),
                                y + paint.fontMetrics.lineHeight * index,
                                paint
                            )
                        }
                    }
                }
        }
    }

    override fun getMaxLabelSize(): Size = labelsProvider.getMaxLabelMaxSize(paint).let {
        it.copy(width = it.width + axisDistance)
    }

    private fun getXPosition(drawScope: DrawScope, textWidth: Float): Float {
        val position = getNormalisedPosition() * drawScope.size.width
        return position + when (side) {
            VerticalLabelSide.RIGHT -> axisDistance
            VerticalLabelSide.LEFT -> -textWidth - axisDistance
        }
    }

    override fun getNormalisedPosition() = when (location) {
        VerticalLabelLocation.RIGHT -> 1f
        VerticalLabelLocation.LEFT -> 0f
    }
}


enum class VerticalLabelLocation { RIGHT, LEFT }

enum class VerticalLabelSide { RIGHT, LEFT }


@Composable
fun verticalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: VerticalLabelLocation = VerticalLabelLocation.LEFT,
    side: VerticalLabelSide = VerticalLabelSide.LEFT,
    axisDistance: Float = 12f,
    labelsProvider: LabelsProvider = IntLabelsProvider,
): VerticalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return VerticalLabelRendererImpl(paint, location, side, axisDistance, labelsProvider)
}

fun verticalLabelRenderer(
    paint: Paint,
    location: VerticalLabelLocation = VerticalLabelLocation.LEFT,
    side: VerticalLabelSide = VerticalLabelSide.LEFT,
    axisDistance: Float = 12f,
    labelsProvider: LabelsProvider = IntLabelsProvider
): VerticalLabelRenderer =
    VerticalLabelRendererImpl(paint, location, side, axisDistance, labelsProvider)