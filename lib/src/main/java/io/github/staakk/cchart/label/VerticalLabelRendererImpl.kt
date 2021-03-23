package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.util.Alignment
import io.github.staakk.cchart.util.countLines
import io.github.staakk.cchart.util.drawText
import io.github.staakk.cchart.util.lineHeight

enum class VerticalLabelSide { RIGHT, LEFT }

@Composable
fun verticalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: Float = 0f,
    alignment: Alignment = Alignment.CenterLeft,
    labelOffset: Offset = Offset(-12f, 0f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
): VerticalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return verticalLabelRenderer(paint, location, alignment, labelOffset, labelsProvider)
}

fun verticalLabelRenderer(
    paint: Paint,
    location: Float = 0f,
    alignment: Alignment = Alignment.CenterLeft,
    labelOffset: Offset = Offset(-12f, 0f),
    labelsProvider: LabelsProvider = IntLabelsProvider
) = VerticalLabelRenderer { context ->
    labelsProvider.provide(context.bounds.minY, context.bounds.maxY)
        .forEach { (text, offset) ->
            val textHeight = paint.fontMetrics.lineHeight * (text.countLines() - 1)
            val y = size.height + context.dataToRendererCoordY(offset) - textHeight / 2
            if (y - textHeight > 0f && y + textHeight / 2 < size.height) {
                drawText(
                    text = text,
                    alignment = alignment,
                    position = Offset(
                        x = location * size.width,
                        y = y
                    ) + labelOffset,
                    paint
                )
            }
        }
}