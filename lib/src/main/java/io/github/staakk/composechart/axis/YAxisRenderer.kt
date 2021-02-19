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


class YAxisRenderer(
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
            start = Offset(0f, 0f),
            end = Offset(0f, size.height)
        )
    }

    override fun DrawScope.renderLabels(context: RendererContext) {
        val paint = Paint().apply {
            typeface = labelsTypeface
            textSize = with(context.density) { labelsTextSize.toPx() }
            isAntiAlias = true
        }
        drawIntoCanvas { canvas ->
            labelsProvider.createLabels(context.bounds.minY, context.bounds.maxY).forEach { (text, offset) ->
                canvas.nativeCanvas.drawText(
                    text,
                    0f - paint.measureText(text) - 10f,
                    size.height + context.dataToRendererCoordY(offset) - paint.fontMetrics.top / 2,
                    paint
                )
            }
        }
    }
}