package io.github.staakk.cchart.samples

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.features
import io.github.staakk.cchart.renderer.label.Labels.Companion.horizontalLabels
import io.github.staakk.cchart.renderer.label.Labels.Companion.verticalLabels
import io.github.staakk.cchart.renderer.axis.Axis
import io.github.staakk.cchart.renderer.axis.AxisOrientation
import io.github.staakk.cchart.renderer.bar.BarProcessor
import io.github.staakk.cchart.style.PrimitiveStyle

@Composable
fun AnimatedBarColorChartScreen() {
    @OptIn(ExperimentalTextApi::class)
    val labels = arrayOf(
        horizontalLabels(),
        verticalLabels(),
    )

    val color0 = remember { Animatable(Color.DarkGray) }
    LaunchedEffect(color0) {
        color0.animateTo(
            Colors.Indigo,
            animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
        )
    }

    val color1 = remember { Animatable(Color.DarkGray) }
    LaunchedEffect(color1) {
        color1.animateTo(
            Colors.Green,
            animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
        )
    }

    Chart(
        modifier = Modifier
            .padding(start = 32.dp, bottom = 32.dp)
            .aspectRatio(1f, false),
        viewport = Viewport(0f, 6f, 0f, 5f)
    ) {
        val styles = listOf(
            PrimitiveStyle(brush = SolidColor(color0.value)),
            PrimitiveStyle(brush = SolidColor(color1.value))
        )
        val barProcessor = BarProcessor(
            preferredWidth = 64f,
            style = { index, _ -> styles[index] },
        )
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
fun PreviewAnimatedBarColorChart() {
    Surface {
        AnimatedBarColorChartScreen()
    }
}