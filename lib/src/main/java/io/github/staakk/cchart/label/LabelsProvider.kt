package io.github.staakk.cchart.label

import android.graphics.Paint
import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.util.lineHeight

interface LabelsProvider {

    fun provide(min: Float, max: Float): List<Pair<String, Float>>

    fun getMaxLength(): Int

    fun getMaxLines(): Int
}

object IntLabelsProvider : LabelsProvider {
    override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
        require(max > min) { "Max should be larger than min. min: $min, max: $max" }
        return (min.toInt()..(max.toInt() + 1)).map { "$it" to it.toFloat() }
    }

    override fun getMaxLength(): Int = 3

    override fun getMaxLines(): Int = 1
}

fun LabelsProvider.getMaxLabelSize(paint: Paint): Size {
    val maxLetterSize = ('0'..'9').maxByOrNull { paint.measureText(it.toString()) } ?: '0'
    return Size(
        width = paint.measureText(String((0..getMaxLength()).map { maxLetterSize }.toCharArray())),
        height = getMaxLines() * paint.fontMetrics.lineHeight
    )
}