package io.github.staakk.composechart.axis

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.composechart.renderer.RendererContext
import io.github.staakk.composechart.util.countLines


class XAxisRenderer(
    private val brush: Brush,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
    private val labelsTextSize: TextUnit = 12.sp,
    private val labelsTypeface: Typeface = Typeface.DEFAULT,
    private val labelsProvider: LabelsProvider = INT_LABELS_PROVIDER,
) : AxisRenderer {

    override fun DrawScope.renderAxis(context: RendererContext) {
        drawLine(
            brush = brush,
            strokeWidth = strokeWidth,
            pathEffect = pathEffect,
            cap = cap,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height)
        )

    }

    override fun DrawScope.renderLabels(context: RendererContext) {
        val paint = Paint().apply {
            typeface = labelsTypeface
            textSize = with(context.density) { labelsTextSize.toPx() }
            isAntiAlias = true
        }
        drawIntoCanvas { canvas ->
            labelsProvider.createLabels(context.bounds.minX, context.bounds.maxX).forEach { (text, offset) ->
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