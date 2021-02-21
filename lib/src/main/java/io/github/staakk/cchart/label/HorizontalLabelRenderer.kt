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

private class HorizontalLabelRenderer(
    private val paint: Paint,
    private val location: HorizontalLabelLocation,
    private val side: HorizontalLabelSide,
    private val axisDistance: Float,
    private val labelsProvider: LabelsProvider,
) : LabelRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.provide(context.bounds.minX, context.bounds.maxX)
                .forEach { (text, offset) ->
                    val textWidth = paint.measureText(text)
                    val x = context.dataToRendererCoordX(offset) - textWidth / 2
                    if (x > 0 && x + textWidth < size.width) {
                        canvas.nativeCanvas.drawText(
                            text,
                            context.dataToRendererCoordX(offset) - paint.measureText(text) / 2,
                            getYPosition(this),
                            paint
                        )
                    }
                }
        }
    }

    override fun getMaxLabelSize(): Size = labelsProvider.getMaxLabelSize(paint).let {
        it.copy(height = it.height + axisDistance)
    }

    private fun getYPosition(drawScope: DrawScope): Float {
        val position = when (location) {
            HorizontalLabelLocation.BOTTOM -> drawScope.size.height
            HorizontalLabelLocation.TOP -> 0f
        }
        val textHeight = paint.fontMetrics.lineHeight * labelsProvider.getMaxLines()
        return position + when (side) {
            HorizontalLabelSide.BELOW -> -textHeight + axisDistance + paint.fontMetrics.bottom
            HorizontalLabelSide.ABOVE -> textHeight - axisDistance
        }
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
): LabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return HorizontalLabelRenderer(paint, location, side, axisDistance, labelsProvider)
}

fun horizontalLabelRenderer(
    paint: Paint,
    location: HorizontalLabelLocation = HorizontalLabelLocation.BOTTOM,
    side: HorizontalLabelSide = HorizontalLabelSide.BELOW,
    axisDistance: Float = 12f,
    labelsProvider: LabelsProvider = IntLabelsProvider
): LabelRenderer = HorizontalLabelRenderer(paint, location, side, axisDistance, labelsProvider)