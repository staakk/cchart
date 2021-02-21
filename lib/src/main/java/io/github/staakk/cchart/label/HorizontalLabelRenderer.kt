package io.github.staakk.cchart.label

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.github.staakk.cchart.renderer.RendererContext

class HorizontalLabelRenderer(
    private val paint: Paint,
    private val location: Location = Location.BOTTOM,
    private val side: Side = Side.UNDER,
    private val labelsProvider: LabelsProvider = IntLabelsProvider,
) : LabelRenderer {

    enum class Location { BOTTOM, TOP }

    enum class Side { UNDER, OVER }

    override fun DrawScope.render(context: RendererContext) {
        drawIntoCanvas { canvas ->
            labelsProvider.provide(context.bounds.minX, context.bounds.maxX)
                .forEach { (text, offset) ->
                    val textWidth = paint.measureText(text)
                    val x = context.dataToRendererCoordX(offset) - textWidth / 2
                    if (x > 0 && x + textWidth < size.width) {
                        canvas.nativeCanvas.drawText(
                            text,
                            context.dataToRendererCoordX(offset) - paint.measureText(text) / 2,
                            getYPosition(this),
                            paint
                        )
                    }
                }
        }
    }

    override fun getMaxLabelSize(): Size = labelsProvider.getMaxLabelSize(paint)

    private fun getYPosition(drawScope: DrawScope): Float {
        val op: Float.(Float) -> Float = when (side) {
            Side.UNDER -> Float::minus
            Side.OVER -> Float::plus
        }
        val position = when (location) {
            Location.BOTTOM -> drawScope.size.height
            Location.TOP -> 0f
        }
        return position.op(paint.fontMetrics.top * labelsProvider.getMaxLines())
    }
}

@Composable
fun horizontalLabelRenderer(
    labelsTextSize: TextUnit = 12.sp,
    labelsTypeface: Typeface = Typeface.DEFAULT,
    location: HorizontalLabelRenderer.Location = HorizontalLabelRenderer.Location.BOTTOM,
    side: HorizontalLabelRenderer.Side = HorizontalLabelRenderer.Side.UNDER,
    labelsProvider: LabelsProvider = IntLabelsProvider,
): HorizontalLabelRenderer {
    val density = LocalDensity.current
    val paint = Paint().apply {
        typeface = labelsTypeface
        textSize = with(density) { labelsTextSize.toPx() }
        isAntiAlias = true
    }
    return HorizontalLabelRenderer(paint, location, side, labelsProvider)
}

fun horizontalLabelRenderer(
    paint: Paint,
    location: HorizontalLabelRenderer.Location = HorizontalLabelRenderer.Location.BOTTOM,
    side: HorizontalLabelRenderer.Side = HorizontalLabelRenderer.Side.UNDER,
    labelsProvider: LabelsProvider = IntLabelsProvider
) = HorizontalLabelRenderer(paint, location, side, labelsProvider)