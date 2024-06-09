package io.github.staakk.cchart.label

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.renderer.Renderer
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.style.LabelStyle
import io.github.staakk.cchart.common.applyExtendedClipping
import io.github.staakk.cchart.common.drawText

class Labels constructor(
    private val textMeasurer: TextMeasurer,
    private val orientation: LabelOrientation,
    private val locationPercent: Float,
    private val labelStyle: LabelStyle = LabelStyle(),
    private val labelsProvider: LabelsProvider = IntLabelsProvider,
) : Renderer {

    companion object {
        @Composable
        fun labels(
            orientation: LabelOrientation,
            locationPercent: Float,
            textMeasurer: TextMeasurer = rememberTextMeasurer(),
            labelStyle: LabelStyle = LabelStyle(),
            labelsProvider: LabelsProvider = IntLabelsProvider,
        ) = Labels(
            textMeasurer = textMeasurer,
            orientation = orientation,
            locationPercent = locationPercent,
            labelStyle = labelStyle,
            labelsProvider = labelsProvider,
        )

        @Composable
        fun horizontalLabels(
            locationPercent: Float = 0.0f,
            textMeasurer: TextMeasurer = rememberTextMeasurer(),
            labelStyle: LabelStyle = LabelStyle(),
            labelsProvider: LabelsProvider = IntLabelsProvider,
        ) = Labels(
            textMeasurer = textMeasurer,
            orientation = LabelOrientation.Horizontal,
            locationPercent = locationPercent,
            labelStyle = labelStyle,
            labelsProvider = labelsProvider,
        )

        @Composable
        fun verticalLabels(
            locationPercent: Float = 0.0f,
            textMeasurer: TextMeasurer = rememberTextMeasurer(),
            labelStyle: LabelStyle = LabelStyle(
                alignment = Alignment.CenterEnd,
                labelOffset = with(LocalDensity.current) { Offset(-4.dp.toPx(), 0f) },
            ),
            labelsProvider: LabelsProvider = IntLabelsProvider,
        ) = Labels(
            textMeasurer = textMeasurer,
            orientation = LabelOrientation.Vertical,
            locationPercent = locationPercent,
            labelStyle = labelStyle,
            labelsProvider = labelsProvider,
        )
    }

    override fun RendererScope.draw() {
        val chartContext = chartContext
        applyExtendedClipping(labelStyle.clip, getClipExtension()) {
            val range = orientation.getRange(chartContext.viewport)
            labelsProvider
                .provide(range.start, range.endInclusive)
                .forEach { (text, offset) ->
                    drawText(
                        textMeasurer = textMeasurer,
                        text = text,
                        style = labelStyle.textStyle,
                        position = labelStyle.labelOffset + with(orientation) {
                            transformToRendererSpace(chartContext, offset, locationPercent)
                        },
                        alignment = labelStyle.alignment,
                        brush = labelStyle.brush,
                    )
                }
        }
    }

    private fun getClipExtension(): Size =
        if (!labelStyle.clip) Size.Zero
        else orientation.getClipExtension(
            textMeasurer,
            labelStyle.labelOffset,
            labelStyle.textStyle
        )
}