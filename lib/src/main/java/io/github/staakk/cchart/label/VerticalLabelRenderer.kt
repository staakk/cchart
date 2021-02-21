package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.axis.INT_LABELS_PROVIDER
import io.github.staakk.cchart.axis.LabelsProvider
import io.github.staakk.cchart.renderer.RendererContext

class VerticalLabelRenderer(
    private val paint: Paint,
    private val labelsProvider: LabelsProvider = INT_LABELS_PROVIDER,
) : LabelRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.createLabels(context.bounds.minY, context.bounds.maxY)
                .forEach { (text, offset) ->
                    val textHeight = paint.fontMetrics.top
                    val y = size.height + context.dataToRendererCoordY(offset) - textHeight / 2
                    if (y + textHeight > 0f && y < size.height) {
                        canvas.nativeCanvas.drawText(
                            text,
                            0f - paint.measureText(text) - 10f,
                            y,
                            paint
                        )
                    }
                }
        }
    }
}

@Composable
fun verticalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    labelsProvider: LabelsProvider = INT_LABELS_PROVIDER,
): VerticalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return VerticalLabelRenderer(paint, labelsProvider)
}

fun verticalLabelRenderer(paint: Paint, labelsProvider: LabelsProvider = INT_LABELS_PROVIDER) =
    HorizontalLabelRenderer(paint, labelsProvider)