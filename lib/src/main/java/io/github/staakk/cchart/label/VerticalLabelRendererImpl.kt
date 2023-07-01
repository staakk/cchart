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
import io.github.staakk.cchart.util.*

@Composable
@OptIn(ExperimentalTextApi::class)
fun verticalLabelRenderer(
    brush: Brush = SolidColor(Color.Black),
    textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    location: Float = 0f,
    alignment: Alignment = Alignment.CenterEnd,
    labelOffset: Offset = Offset(-12f, 0f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
): VerticalLabelRenderer {
    val textMeasurer = rememberTextMeasurer()
    return verticalLabelRenderer(
        textMeasurer = textMeasurer,
        brush = brush,
        style = textStyle,
        alignment = alignment,
        location = location,
        labelOffset = labelOffset,
        labelsProvider = labelsProvider,
    )
}

@OptIn(ExperimentalTextApi::class)
fun verticalLabelRenderer(
    brush: Brush,
    textMeasurer: TextMeasurer,
    style: TextStyle,
    alignment: Alignment,
    location: Float = 1f,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider
): VerticalLabelRenderer = VerticalLabelRenderer { context ->
    labelsProvider.provide(context.viewport.minY, context.viewport.maxY)
        .forEach { (text, offset) ->
            val textHeight = textMeasurer.measure(text, style).size.height
            val y = context.toRendererY(offset) - textHeight / 2
            if (y + textHeight / 2 < size.height && y - textHeight / 2 > 0f) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = text,
                    style = style,
                    position = Offset(
                        x = location * size.width,
                        y = y
                    ) + labelOffset,
                    alignment = alignment,
                    brush = brush,
                )
            }
        }
}