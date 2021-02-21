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

private class VerticalLabelRenderer(
    private val paint: Paint,
    private val location: VerticalLabelLocation,
    private val side: VerticalLabelSide,
    private val axisDistance: Float,
    private val labelsProvider: LabelsProvider,
) : LabelRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.provide(context.bounds.minY, context.bounds.maxY)
                .forEach { (text, offset) ->
                    val textHeight =
                        paint.fontMetrics.lineHeight * text.countLines()
                    val y = size.height + context.dataToRendererCoordY(offset) - textHeight / 2
                    if (y + textHeight > 0f && y < size.height) {
                        canvas.nativeCanvas.drawText(
                            text,
                            getXPosition(this, paint.measureText(text)),
                            y,
                            paint
                        )
                    }
                }
        }
    }

    override fun getMaxLabelSize(): Size = labelsProvider.getMaxLabelSize(paint).let {
        it.copy(width = it.width + axisDistance)
    }

    private fun getXPosition(drawScope: DrawScope, textWidth: Float): Float {
        val position = when (location) {
            VerticalLabelLocation.RIGHT -> drawScope.size.width
            VerticalLabelLocation.LEFT -> 0f
        }
        return position + when (side) {
            VerticalLabelSide.RIGHT -> axisDistance
            VerticalLabelSide.LEFT -> -textWidth - axisDistance
        }
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
): LabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return VerticalLabelRenderer(paint, location, side, axisDistance, labelsProvider)
}

fun verticalLabelRenderer(
    paint: Paint,
    location: VerticalLabelLocation = VerticalLabelLocation.LEFT,
    side: VerticalLabelSide = VerticalLabelSide.LEFT,
    axisDistance: Float = 12f,
    labelsProvider: LabelsProvider = IntLabelsProvider
): LabelRenderer = VerticalLabelRenderer(paint, location, side, axisDistance, labelsProvider)