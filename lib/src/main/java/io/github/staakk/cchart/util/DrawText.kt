package io.github.staakk.cchart.util

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.util.Alignment.Companion.CenterLeft


fun DrawScope.drawText(
    text: String,
    position: Offset,
    alignment: Alignment,
    textAlignment: TextAlignment,
    paint: Paint
) {
    var maxLength = 0f
    val lines = text.lines()
        .map {
            val length = paint.measureText(it)
            if (length > maxLength) maxLength = length
            it to length
        }
    drawIntoCanvas { canvas ->
        lines.forEachIndexed { index, line ->
            val pos = alignment.getDrawingPosition(
                position,
                line,
                index,
                lines.size,
                maxLength,
                textAlignment,
                paint
            )
            canvas.nativeCanvas.drawText(line.first, pos.x, pos.y, paint)
        }
    }
}

fun interface TextAlignment {

    fun getHorizontalOffset(lineLength: Float, maxLineLength: Float): Float

    companion object {

        val Left = TextAlignment { _, _ -> 0f }

        val Center = TextAlignment { lineLength, maxLineLength ->
            (maxLineLength - lineLength) / 2f
        }

        val Right = TextAlignment { lineLength, maxLineLength ->
            maxLineLength - lineLength
        }
    }
}

fun interface Alignment {

    fun getDrawingPosition(
        position: Offset,
        line: Pair<String, Float>,
        lineNo: Int,
        maxLines: Int,
        maxLength: Float,
        textAlignment: TextAlignment,
        paint: Paint
    ): Offset

    companion object {

        val BottomRight =
            Alignment { position, (_, length), lineNo, _, maxLength, textAlignment, paint ->
                Offset(
                    x = position.x + textAlignment.getHorizontalOffset(length, maxLength),
                    y = position.y + paint.fontMetrics.lineHeight * lineNo - paint.fontMetrics.top
                )
            }

        val BottomCenter =
            Alignment { position, (_, length), lineNo, _, maxLength, textAlignment, paint ->
                Offset(
                    x = position.x - maxLength / 2 + +textAlignment.getHorizontalOffset(
                        length,
                        maxLength
                    ),
                    y = position.y + paint.fontMetrics.lineHeight * lineNo - paint.fontMetrics.top
                )
            }

        val BottomLeft =
            Alignment { position, (_, length), lineNo, _, maxLength, textAlignment, paint ->
                Offset(
                    x = position.x - maxLength + textAlignment.getHorizontalOffset(
                        length,
                        maxLength
                    ),
                    y = position.y + paint.fontMetrics.lineHeight * lineNo - paint.fontMetrics.top
                )
            }

        val CenterLeft =
            Alignment { position, (_, length), lineNo, maxLines, maxLength, textAlignment, paint ->
                val lineHeight = paint.fontMetrics.lineHeight
                Offset(
                    x = position.x - maxLength + textAlignment.getHorizontalOffset(
                        length,
                        maxLength
                    ),
                    y = position.y + lineHeight * (lineNo - maxLines / 2f) - paint.fontMetrics.top
                )
            }

        val Center =
            Alignment { position, (_, length), lineNo, maxLines, maxLength, textAlignment, paint ->
                val lineHeight = paint.fontMetrics.lineHeight
                Offset(
                    x = position.x - maxLength / 2 + textAlignment.getHorizontalOffset(
                        length,
                        maxLength
                    ),
                    y = position.y + lineHeight * (lineNo - maxLines / 2f) - paint.fontMetrics.top
                )
            }

        val CenterRight =
            Alignment { position, (_, length), lineNo, maxLines, maxLength, textAlignment, paint ->
                val lineHeight = paint.fontMetrics.lineHeight
                Offset(
                    x = position.x + textAlignment.getHorizontalOffset(length, maxLength),
                    y = position.y + lineHeight * (lineNo - maxLines / 2f) - paint.fontMetrics.top
                )
            }

        val TopLeft =
            Alignment { position, (_, length), lineNo, maxLines, maxLength, textAlignment, paint ->
                val lineHeight = paint.fontMetrics.lineHeight
                Offset(
                    x = position.x - maxLength + textAlignment.getHorizontalOffset(
                        length,
                        maxLength
                    ),
                    y = position.y + lineHeight * (lineNo - maxLines) - paint.fontMetrics.top
                )
            }

        val TopCenter =
            Alignment { position, (_, length), lineNo, maxLines, maxLength, textAlignment, paint ->
                val lineHeight = paint.fontMetrics.lineHeight
                Offset(
                    x = position.x - maxLength / 2 + textAlignment.getHorizontalOffset(
                        length,
                        maxLength
                    ),
                    y = position.y + lineHeight * (lineNo - maxLines) - paint.fontMetrics.top
                )
            }

        val TopRight =
            Alignment { position, (_, length), lineNo, maxLines, maxLength, textAlignment, paint ->
                val lineHeight = paint.fontMetrics.lineHeight
                Offset(
                    x = position.x + textAlignment.getHorizontalOffset(length, maxLength),
                    y = position.y + lineHeight * (lineNo - maxLines) - paint.fontMetrics.top
                )
            }
    }
}

@Preview
@Composable
fun Preview() {
    val density = LocalDensity.current
    val midPx = with(density) { 50.dp.toPx() }
    Surface(modifier = Modifier.size(100.dp, 100.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawLine(
                color = Color.Blue,
                start = Offset(0f, midPx),
                end = Offset(midPx * 2, midPx)
            )
            drawLine(
                color = Color.Blue,
                start = Offset(midPx, 0f),
                end = Offset(midPx, midPx * 2)
            )
            drawText(
                text = "qde\nasdayf\nwer\npoi",
                position = with(density) { Offset(50.dp.toPx(), 50.dp.toPx()) },
                alignment = CenterLeft,
                textAlignment = TextAlignment.Center,
                paint = Paint()
            )
        }
    }
}