package io.github.staakk.cchart.label

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.renderer.ChartContext
import io.github.staakk.cchart.renderer.RendererScope
import io.github.staakk.cchart.util.applyExtendedClipping
import io.github.staakk.cchart.util.drawText

fun interface LabelRenderer {
    fun RendererScope.render()
}

@Composable
@OptIn(ExperimentalTextApi::class)
fun labelRenderer(
    orientation: LabelOrientation,
    brush: Brush = SolidColor(Color.Black),
    locationPercent: Float = 0f,
    textStyle: TextStyle = TextStyle.Default.copy(fontSize = 12.sp),
    alignment: Alignment = Alignment.TopCenter,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
    clip: Boolean = false,
): LabelRenderer {
    val textMeasurer = rememberTextMeasurer()
    return labelRenderer(
        orientation = orientation,
        textMeasurer = textMeasurer,
        brush = brush,
        style = textStyle,
        alignment = alignment,
        labelOffset = labelOffset,
        labelsProvider = labelsProvider,
        clipExtension = if (!clip) Size.Zero else orientation.getClipExtension(
            textMeasurer,
            labelOffset,
            textStyle
        )
    ) { context: ChartContext, offset: Float ->
        with(orientation) { transformToRendererSpace(context, offset, locationPercent) }
    }
}


@OptIn(ExperimentalTextApi::class)
fun labelRenderer(
    orientation: LabelOrientation,
    textMeasurer: TextMeasurer,
    brush: Brush,
    style: TextStyle,
    alignment: Alignment,
    labelOffset: Offset = Offset(0f, 12f),
    labelsProvider: LabelsProvider = IntLabelsProvider,
    clipExtension: Size,
    labelDataLocationToRenderer: DrawScope.(ChartContext, Float) -> Offset,
): LabelRenderer = LabelRenderer {
    val chartContext = chartContext
    applyExtendedClipping(
        enabled = clipExtension != Size.Zero,
        extension = clipExtension,
    ) {
        val range = orientation.getRange(chartContext.viewport)
        labelsProvider
            .provide(range.start, range.endInclusive)
            .forEach { (text, offset) ->
                drawText(
                    textMeasurer = textMeasurer,
                    text = text,
                    style = style,
                    position = labelDataLocationToRenderer(chartContext, offset) + labelOffset,
                    alignment = alignment,
                    brush = brush,
                )
            }
    }
}

@OptIn(ExperimentalTextApi::class)
sealed class LabelOrientation {
    abstract fun getClipExtension(
        textMeasurer: TextMeasurer,
        labelOffset: Offset,
        style: TextStyle
    ): Size

    abstract fun DrawScope.transformToRendererSpace(
        context: ChartContext,
        offset: Float,
        locationPercent: Float,
    ): Offset

    abstract fun getRange(viewport: Viewport): ClosedFloatingPointRange<Float>

    object Horizontal : LabelOrientation() {
        override fun getClipExtension(
            textMeasurer: TextMeasurer,
            labelOffset: Offset,
            style: TextStyle
        ) = Size(
            width = 0f,
            height = labelOffset.y + textMeasurer.measure("0", style).size.height,
        )

        override fun DrawScope.transformToRendererSpace(
            context: ChartContext,
            offset: Float,
            locationPercent: Float,
        ): Offset = Offset(
            x = context.toRendererX(offset),
            y = (1f - locationPercent) * size.height,
        )

        override fun getRange(viewport: Viewport) = viewport.minX..viewport.maxX
    }

    object Vertical : LabelOrientation() {
        override fun getClipExtension(
            textMeasurer: TextMeasurer,
            labelOffset: Offset,
            style: TextStyle
        ) = Size(
            width = labelOffset.x + textMeasurer.measure("MMMMM", style).size.width,
            height = 0f,
        )

        override fun DrawScope.transformToRendererSpace(
            context: ChartContext,
            offset: Float,
            locationPercent: Float,
        ): Offset = Offset(
            x = locationPercent * size.width,
            y = context.toRendererY(offset),
        )

        override fun getRange(viewport: Viewport) = viewport.minY..viewport.maxY
    }
}


@Composable
fun defaultHorizontalLabelRenderer() = labelRenderer(
    orientation = LabelOrientation.Horizontal,
    locationPercent = 0f,
    clip = true,
)

@Composable
fun defaultVerticalLabelRenderer() = labelRenderer(
    orientation = LabelOrientation.Vertical,
    alignment = Alignment.CenterEnd,
    labelOffset = with(LocalDensity.current) { Offset(-4.dp.toPx(), 0f) },
    locationPercent = 0f,
    clip = true,
)