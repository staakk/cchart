package io.github.staakk.cchart.label

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.util.drawText

@Composable
@OptIn(ExperimentalTextApi::class)
fun horizontalLabelRenderer(
    textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    location: Float = 1f,
    alignment: Alignment = Alignment.TopCenter,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
): HorizontalLabelRenderer {
    val textMeasurer = rememberTextMeasurer()
    return horizontalLabelRenderer(
        textMeasurer = textMeasurer,
        brush = SolidColor(Color.Black),
        style = textStyle,
        alignment = alignment,
        location = location,
        labelOffset = labelOffset,
        labelsProvider = labelsProvider,
    )
}

@OptIn(ExperimentalTextApi::class)
fun horizontalLabelRenderer(
    brush: Brush,
    textMeasurer: TextMeasurer,
    style: TextStyle,
    alignment: Alignment,
    location: Float = 1f,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider
): HorizontalLabelRenderer = HorizontalLabelRenderer { context ->
    labelsProvider.provide(context.viewport.minX, context.viewport.maxX)
        .forEach { (text, offset) ->
            val textWidth = textMeasurer.measure(text, style).size.width
            val x = context.toRendererX(offset) - textWidth / 2
            if (x > 0 && x + textWidth < size.width) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = text,
                    style = style,
                    position = Offset(
                        x = context.toRendererX(offset),
                        y = location * size.height
                    ) + labelOffset,
                    alignment = alignment,
                    brush = brush,
                )
            }
        }
}