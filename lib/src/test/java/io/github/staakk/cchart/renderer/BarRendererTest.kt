package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.groupedSeriesOf
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.matchesRect
import io.mockk.mockk
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.Test

class BarRendererTest {

    private val drawScope = mockk<DrawScope>()

    private val rendererContext = rendererContext(
        viewport = Viewport(minX = 0f, maxX = 1f, minY = 0f, maxY = 1f),
        canvasSize = Size(2f, 2f)
    )

    private val series = groupedSeriesOf(
        listOf(
            pointOf(-1f, 0.5f),
            pointOf(-1f, 0.3f),
        ),
        listOf(
            pointOf(0.5f, 0.5f),
            pointOf(0.5f, 0.3f),
        ),
        listOf(
            pointOf(0.7f, 0.5f),
            pointOf(0.7f, 0.3f),
        ),
        listOf(
            pointOf(1.5f, 0.5f),
            pointOf(1.5f, 0.3f),
        ),
    )

    @Test
    fun `should draw bars`() {
        val expected = listOf(
            BoundingShape.Rect(
                data = pointOf(x = -1f, y = 0.5f),
                labelAnchorX = -2.05f,
                labelAnchorY = 1f,
                topLeft = Offset(-2.1f, 1f),
                bottomRight = Offset(-2.0f, 2f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = -1.0f, y = 0.3f),
                labelAnchorX = -1.95f,
                labelAnchorY = 1.4f,
                topLeft = Offset(-2.0f, 1.4f),
                bottomRight = Offset(-1.9f, 2.0f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = 0.5f, y = 0.5f),
                labelAnchorX = 0.95f,
                labelAnchorY = 1f,
                topLeft = Offset(0.9f, 1f),
                bottomRight = Offset(1f, 2f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = 0.5f, y = 0.3f),
                labelAnchorX = 1.05f,
                labelAnchorY = 1.4f,
                topLeft = Offset(1.0f, 1.4f),
                bottomRight = Offset(1.1f, 2f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = 0.7f, y = 0.5f),
                labelAnchorX = 1.3499999f,
                labelAnchorY = 1f,
                topLeft = Offset(1.3f, 1f),
                bottomRight = Offset(1.4f, 2f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = 0.7f, y = 0.3f),
                labelAnchorX = 1.4499999f,
                labelAnchorY = 1.4f,
                topLeft = Offset(1.4f, 1.4f),
                bottomRight = Offset(1.5f, 2f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = 1.5f, y = 0.5f),
                labelAnchorX = 2.95f,
                labelAnchorY = 1f,
                topLeft = Offset(2.9f, 1f),
                bottomRight = Offset(3f, 2f)
            ),
            BoundingShape.Rect(
                data = pointOf(x = 1.5f, y = 0.3f),
                labelAnchorX = 3.05f,
                labelAnchorY = 1.4f,
                topLeft = Offset(3.0f, 1.4f),
                bottomRight = Offset(3.1f, 2f)
            )
        )
        val result = with(barGroupRenderer(
            preferredWidth = 0.1f,
            minimalSpacing = 0.1f,
            barDrawer = { _, _, _, _ -> }
        )) {
            drawScope.render(rendererContext, series)
        }

        assertThat("", result, contains(*expected.map { matchesRect(it) }.toTypedArray()))
    }
}