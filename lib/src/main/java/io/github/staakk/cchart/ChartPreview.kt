package io.github.staakk.cchart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import io.github.staakk.cchart.axis.horizontalAxisRenderer
import io.github.staakk.cchart.axis.verticalAxisRenderer
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.github.staakk.cchart.grid.GridOrientation
import io.github.staakk.cchart.grid.gridRenderer
import io.github.staakk.cchart.label.HorizontalLabelLocation
import io.github.staakk.cchart.label.HorizontalLabelSide
import io.github.staakk.cchart.label.horizontalLabelRenderer
import io.github.staakk.cchart.label.verticalLabelRenderer
import io.github.staakk.cchart.renderer.barRenderer
import io.github.staakk.cchart.renderer.lineRenderer
import io.github.staakk.cchart.renderer.pointRenderer


@Preview(name = "Chart")
@Composable
internal fun PreviewCoordinatePlane() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Chart(
                modifier = Modifier.weight(1f),
                viewport = Viewport(-1f, 5f, 0f, 9f)
            ) {
                series(
                    seriesOf(
                        "First",
                        pointOf(0f, 1f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    renderer = pointRenderer(SolidColor(Color.Blue), 10f)
                )
                series(
                    seriesOf(
                        "Second",
                        pointOf(0f, 0f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    renderer = lineRenderer(SolidColor(Color.Green))
                )
                series(
                    seriesOf(
                        "Third",
                        pointOf(0f, 1f),
                        pointOf(2f, 8f),
                        pointOf(3f, 3f),
                        pointOf(4f, 4f),
                    ),
                    renderer = barRenderer({ SolidColor(Color.Red) }, 15f)
                )

                grid(
                    gridRenderer(
                        orientation = GridOrientation.VERTICAL,
                        gridLinesProvider = { min, max ->
                            (min.toInt()..max.toInt()).map { it }
                                .filter { it % 2 == 1 }
                                .map { it.toFloat() }
                        },
                        alpha = 1.0f
                    )
                )
                grid(gridRenderer(orientation = GridOrientation.HORIZONTAL))

                horizontalAxis(horizontalAxisRenderer())

                horizontalAxisLabels(horizontalLabelRenderer())

                verticalAxis(verticalAxisRenderer())

                verticalAxisLabels(verticalLabelRenderer())

                horizontalAxisLabels(
                    horizontalLabelRenderer(
                        location = HorizontalLabelLocation.TOP,
                        side = HorizontalLabelSide.ABOVE
                    )
                )

                dataLabels {
                    Text(text = "$seriesName ${point.x} ${point.y}")
                }

            }

            Text(modifier = Modifier.weight(1f), text = "Another fine chart")
        }
    }
}