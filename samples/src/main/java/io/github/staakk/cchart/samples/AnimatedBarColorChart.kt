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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.barGroupRenderer
import io.github.staakk.cchart.renderer.drawBar

@Composable
fun AnimatedBarColorChartScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()

    val color0 = remember { Animatable(Color.DarkGray) }
    LaunchedEffect(color0) {
        color0.animateTo(
            Colors.Indigo,
            animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
        )
    }

    val color1 = remember { Animatable(Color.DarkGray) }
    LaunchedEffect(color0) {
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
        series(
            groupedSeriesOf(
                listOf(
                    pointOf(1f, 1f),
                    pointOf(1f, 1.5f),
                ),
                listOf(
                    pointOf(2f, 1.5f),
                    pointOf(2f, 1f),
                ),
                listOf(
                    pointOf(3f, 4f),
                    pointOf(3f, 4.5f),
                ),
                listOf(
                    pointOf(4f, 3.5f),
                    pointOf(4f, 3.5f),
                ),
                listOf(
                    pointOf(5f, 2f),
                    pointOf(5f, 1f)
                )
            ),
            renderer = barGroupRenderer(
                preferredWidth = 64f,
                barDrawer = drawBar { index, _ ->
                    SolidColor(
                        when (index) {
                            0 -> color0.value
                            1 -> color1.value
                            else -> Colors.Pink
                        }
                    )
                }
            )
        )

        horizontalAxis(horizontalAxisRenderer())

        horizontalAxisLabels(horizontalLabelRenderer)

        verticalAxis(verticalAxisRenderer())

        verticalAxisLabels(verticalLabelRenderer)
    }
}

@Preview
@Composable
fun PreviewAnimatedBarColorChart() {
    Surface {
        AnimatedBarColorChartScreen()
    }
}