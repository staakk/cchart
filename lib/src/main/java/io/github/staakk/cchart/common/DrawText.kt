package io.github.staakk.cchart.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toOffset
import org.jetbrains.compose.ui.tooling.preview.Preview


fun DrawScope.drawText(
    textMeasurer: TextMeasurer,
    text: String,
    style: TextStyle = TextStyle.Default,
    position: Offset = Offset.Zero,
    alignment: Alignment = Alignment.Center,
    brush: Brush = SolidColor(Color.Black),
) {
    val result = textMeasurer.measure(
        text = text,
        style = style,
    )
    val topLeft = position + alignment
        .align(result.size, IntSize.Zero, LayoutDirection.Ltr)
        .toOffset()
    drawText(
        textLayoutResult = result,
        topLeft = topLeft,
        brush = brush,
    )
}

@Preview
@Composable
private fun PreviewDrawText() {
    val density = LocalDensity.current
    val midPx = with(density) { 50.dp.toPx() }
    val textMeasurer = rememberTextMeasurer()
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
                textMeasurer = textMeasurer,
                text = "qde\nasdayf\nwer\npoi",
                style = TextStyle.Default.copy(textAlign = TextAlign.End, fontSize = 8.sp),
                position = with(density) { Offset(50.dp.toPx(), 50.dp.toPx()) },
                alignment = Alignment.TopEnd,
            )
        }
    }
}