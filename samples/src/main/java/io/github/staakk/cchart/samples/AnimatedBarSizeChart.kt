package io.github.staakk.cchart.samples

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import io.github.staakk.cchart.Chart
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.barGroupRenderer

@Composable
fun AnimatedBarSizeChartScreen() {
    val horizontalLabelRenderer = horizontalLabelRenderer()
    val verticalLabelRenderer = verticalLabelRenderer()

    val trigger = remember { mutableStateOf(false) }
    val heightScale = animateFloatAsState(
        targetValue = if (trigger.value) 1.0f else 0.0f,
        animationSpec = tween(1000)
    )
    LaunchedEffect(heightScale) {
        trigger.value = true
    }

    Chart(
        modifier = Modifier.aspectRatio(1f, false),
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
                barDrawer = { index, _, topLeft, size ->
                    drawRect(
                        brush = SolidColor(
                            when (index) {
                                0 -> Colors.Blue
                                1 -> Colors.Red
                                else -> Colors.Pink
                            }
                        ),
                        topLeft = topLeft,
                        size = size.copy(height = size.height * heightScale.value)
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
fun PreviewAnimatedBarSizeChart() {
    Surface {
        AnimatedBarSizeChartScreen()
    }
}