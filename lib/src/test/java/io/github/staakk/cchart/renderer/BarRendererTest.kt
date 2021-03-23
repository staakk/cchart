package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Point
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
    fun `should draw line`() {
        val expected = listOf(
            BoundingShape.Rect(
                point = Point(x = -1.0f, y = 0.5f, tag = null),
                labelAnchorX = -2.05f,
                labelAnchorY = -1.0f,
                topLeft = Offset(-2.1f, -0.0f),
                bottomRight = Offset(-2.0f, 1.0f)
            ),
            BoundingShape.Rect(
                point = Point(x = -1.0f, y = 0.3f, tag = null),
                labelAnchorX = -1.95f,
                labelAnchorY = -0.6f,
                topLeft = Offset(-2.0f, -0.0f),
                bottomRight = Offset(-1.9f, 0.6f)
            ),
            BoundingShape.Rect(
                point = Point(x = 0.5f, y = 0.5f, tag = null),
                labelAnchorX = 0.95f,
                labelAnchorY = -1.0f,
                topLeft = Offset(0.9f, -0.0f),
                bottomRight = Offset(1.0f, 1.0f)
            ),
            BoundingShape.Rect(
                point = Point(x = 0.5f, y = 0.3f, tag = null),
                labelAnchorX = 1.05f,
                labelAnchorY = -0.6f,
                topLeft = Offset(1.0f, -0.0f),
                bottomRight = Offset(1.1f, 0.6f)
            ),
            BoundingShape.Rect(
                point = Point(x = 0.7f, y = 0.5f, tag = null),
                labelAnchorX = 1.3499999f,
                labelAnchorY = -1.0f,
                topLeft = Offset(1.3f, -0.0f),
                bottomRight = Offset(1.4f, 1.0f)
            ),
            BoundingShape.Rect(
                point = Point(x = 0.7f, y = 0.3f, tag = null),
                labelAnchorX = 1.4499999f,
                labelAnchorY = -0.6f,
                topLeft = Offset(1.4f, -0.0f),
                bottomRight = Offset(1.5f, 0.6f)
            ),
            BoundingShape.Rect(
                point = Point(x = 1.5f, y = 0.5f, tag = null),
                labelAnchorX = 2.95f,
                labelAnchorY = -1.0f,
                topLeft = Offset(2.9f, -0.0f),
                bottomRight = Offset(3.0f, 1.0f)
            ),
            BoundingShape.Rect(
                point = Point(x = 1.5f, y = 0.3f, tag = null),
                labelAnchorX = 3.05f,
                labelAnchorY = -0.6f,
                topLeft = Offset(3.0f, -0.0f),
                bottomRight = Offset(3.1f, 0.6f)
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