package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.util.Alignment
import io.github.staakk.cchart.util.drawText

@Composable
fun horizontalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: Float = 1f,
    alignment: Alignment = Alignment.BottomCenter,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
): HorizontalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return horizontalLabelRenderer(paint, location, alignment, labelOffset, labelsProvider)
}

fun horizontalLabelRenderer(
    paint: Paint,
    location: Float = 1f,
    alignment: Alignment = Alignment.BottomCenter,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider
): HorizontalLabelRenderer = HorizontalLabelRenderer { context ->
    labelsProvider.provide(context.bounds.minX, context.bounds.maxX)
        .forEach { (text, offset) ->
            val textWidth = paint.measureText(text)
            val x = context.dataToRendererCoordX(offset) - textWidth / 2
            if (x > 0 && x + textWidth < size.width) {
                drawText(
                    text = text,
                    alignment = alignment,
                    position = Offset(
                        x = context.dataToRendererCoordX(offset),
                        y = location * size.height
                    ) + labelOffset,
                    paint
                )
            }
        }
}