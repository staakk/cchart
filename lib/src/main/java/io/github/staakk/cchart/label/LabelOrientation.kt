package io.github.staakk.cchart.label

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.renderer.ChartContext

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