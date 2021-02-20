package io.github.staakk.cchart.axis

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.renderer.RendererContext


class YAxisRenderer(
    private val brush: Brush,
    private val strokeWidth: Float = Stroke.HairlineWidth,
    private val pathEffect: PathEffect? = null,
    private val cap: StrokeCap = Stroke.DefaultCap,
    private val alpha: Float = 0.2f,
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
            alpha = alpha,
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