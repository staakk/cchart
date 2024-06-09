package io.github.staakk.cchart.samples

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor

import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.axis.Axis
import io.github.staakk.cchart.axis.AxisOrientation
import io.github.staakk.cchart.bar.BarProcessor
import io.github.staakk.cchart.style.PrimitiveStyle
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AnimatedBarSizeChartScreen() {

    val labels = arrayOf(
        horizontalLabels(),
        verticalLabels(),
    )

    val trigger = remember { mutableStateOf(false) }
    val heightScale = animateFloatAsState(
        targetValue = if (trigger.value) 1.0f else 0.0f,
        animationSpec = tween(1000)
    )
    LaunchedEffect(heightScale) {
        trigger.value = true
    }

    val styles = listOf(
        PrimitiveStyle(brush = SolidColor(Colors.Blue)),
        PrimitiveStyle(brush = SolidColor(Colors.Red))
    )
    val barProcessor = BarProcessor(
        preferredWidth = 64f,
        style = { index, _ -> styles[index] },
        sizeTransform = { it.copy(height = it.height * heightScale.value) }
    )

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 6f, 0f, 5f)
    ) {
        series(
            seriesOf(
                pointOf(1f, 1f),
                pointOf(1f, 1.5f),
                pointOf(2f, 1.5f),
                pointOf(2f, 1f),
                pointOf(3f, 4f),
                pointOf(3f, 4.5f),
                pointOf(4f, 3.5f),
                pointOf(4f, 3.5f),
                pointOf(5f, 2f),
                pointOf(5f, 1f)
            ),
            barProcessor,
        )

        features(
            Axis(AxisOrientation.Horizontal, 0.0f),
            Axis(AxisOrientation.Vertical, 0.0f),
            *labels,
        )
    }
}

@Preview
@Composable
fun PreviewAnimatedBarSizeChart() {
    Surface {
        AnimatedBarSizeChartScreen()
    }
}