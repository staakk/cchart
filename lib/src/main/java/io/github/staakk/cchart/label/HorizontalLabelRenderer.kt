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
import io.github.staakk.cchart.util.countLines

class HorizontalLabelRenderer(
    private val paint: Paint,
    private val labelsProvider: LabelsProvider = INT_LABELS_PROVIDER,
) : LabelRenderer {

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.createLabels(context.bounds.minX, context.bounds.maxX)
                .forEach { (text, offset) ->
                    val textWidth = paint.measureText(text)
                    val x = context.dataToRendererCoordX(offset) - textWidth / 2
                    if (x > 0 && x + textWidth < size.width) {
                        canvas.nativeCanvas.drawText(
                            text,
                            context.dataToRendererCoordX(offset) - paint.measureText(text) / 2,
                            size.height - paint.fontMetrics.top * text.countLines(),
                            paint
                        )
                    }
                }
        }
    }
}

@Composable
fun horizontalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    labelsProvider: LabelsProvider = INT_LABELS_PROVIDER,
): HorizontalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return HorizontalLabelRenderer(paint, labelsProvider)
}

fun horizontalLabelRenderer(paint: Paint, labelsProvider: LabelsProvider = INT_LABELS_PROVIDER) =
    HorizontalLabelRenderer(paint, labelsProvider)