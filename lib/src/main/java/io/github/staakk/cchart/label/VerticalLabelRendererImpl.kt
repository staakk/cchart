package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.util.*

@Composable
fun verticalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: Float = 0f,
    alignment: Alignment = Alignment.CenterLeft,
    textAlignment: TextAlignment = TextAlignment.Left,
    labelOffset: Offset = Offset(-12f, 0f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
): VerticalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return verticalLabelRenderer(
        paint,
        location,
        alignment,
        textAlignment,
        labelOffset,
        labelsProvider
    )
}

/**
 * Creates a [VerticalLabelRenderer].
 *
 * @param paint Paint to draw the labels text with.
 * @param location Location of the label in percents.
 * @param alignment The alignment of the label relative to the position at which it should be
 * rendered.
 * @param textAlignment The alignment of the label text.
 * @param labelOffset Offset of the label position relative to the position at which it should be
 * rendered.
 * @param labelsProvider Provides the labels text and position for the given range.
 */
fun verticalLabelRenderer(
    paint: Paint,
    location: Float = 0f,
    alignment: Alignment = Alignment.CenterLeft,
    textAlignment: TextAlignment = TextAlignment.Left,
    labelOffset: Offset = Offset(-12f, 0f),
    labelsProvider: LabelsProvider = IntLabelsProvider
) = VerticalLabelRenderer { context ->
    labelsProvider.provide(context.viewport.minY, context.viewport.maxY)
        .forEach { (text, offset) ->
            val textHeight = paint.fontMetrics.lineHeight * (text.countLines() - 1)
            val y = context.dataToRendererCoordY(offset) - textHeight / 2
            if (y + textHeight / 2 < size.height && y - textHeight / 2 > 0f) {
                drawText(
                    text = text,
                    alignment = alignment,
                    position = Offset(
                        x = location * size.width,
                        y = y
                    ) + labelOffset,
                    textAlignment = textAlignment,
                    paint = paint
                )
            }
        }
}