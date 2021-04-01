package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.util.Alignment
import io.github.staakk.cchart.util.TextAlignment
import io.github.staakk.cchart.util.drawText

@Composable
fun horizontalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: Float = 1f,
    alignment: Alignment = Alignment.BottomCenter,
    textAlignment: TextAlignment = TextAlignment.Left,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
): HorizontalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return horizontalLabelRenderer(
        paint,
        location,
        alignment,
        textAlignment,
        labelOffset,
        labelsProvider
    )
}

/**
 * Creates a [HorizontalLabelRenderer].
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
fun horizontalLabelRenderer(
    paint: Paint,
    location: Float = 1f,
    alignment: Alignment = Alignment.BottomCenter,
    textAlignment: TextAlignment = TextAlignment.Left,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider
): HorizontalLabelRenderer = HorizontalLabelRenderer { context ->
    labelsProvider.provide(context.viewport.minX, context.viewport.maxX)
        .forEach { (text, offset) ->
            val textWidth = paint.measureText(text)
            val x = context.toRendererX(offset) - textWidth / 2
            if (x > 0 && x + textWidth < size.width) {
                drawText(
                    text = text,
                    position = Offset(
                        x = context.toRendererX(offset),
                        y = location * size.height
                    ) + labelOffset,
                    alignment = alignment,
                    textAlignment = textAlignment,
                    paint = paint
                )
            }
        }
}