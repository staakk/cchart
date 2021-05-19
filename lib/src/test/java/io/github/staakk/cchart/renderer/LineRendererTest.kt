package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class LineRendererTest {

    private val drawScope = mockk<DrawScope>()

    private val rendererContext = chartContext(
        viewport = Viewport(minX = 0f, maxX = 1f, minY = 0f, maxY = 1f),
        canvasSize = Size(2f, 2f)
    )

    private val series = seriesOf(
        pointOf(-1, 0.5f),
        pointOf(-0.5, 0.5f),
        pointOf(0.5, 0.5f),
        pointOf(1.5, 0.5f),
        pointOf(2.0, 0.5f),
        pointOf(1.5, 0.5f),
        pointOf(0.5, 0.5f),
        pointOf(-0.5, 0.5f),
        pointOf(-1, 0.5f),
    )

    @Test
    fun `should draw line`() {
        val expected = listOf<BoundingShape>(
            BoundingShape.Circle(
                data = pointOf(x = -0.5f, y = 0.5f),
                labelAnchorX = -1.0f,
                labelAnchorY = 1.0f,
                center = Offset(-1.0f, 1.0f),
                radius = 20.0f
            ),
            BoundingShape.Circle(
                data = pointOf(x = 0.5f, y = 0.5f),
                labelAnchorX = 1.0f,
                labelAnchorY = 1.0f,
                center = Offset(1.0f, 1.0f),
                radius = 20.0f
            ),
            BoundingShape.Circle(
                data = pointOf(x = 1.5f, y = 0.5f),
                labelAnchorX = 3.0f,
                labelAnchorY = 1.0f,
                center = Offset(3.0f, 1.0f),
                radius = 20.0f
            ),
            BoundingShape.Circle(
                data = pointOf(x = 1.5f, y = 0.5f),
                labelAnchorX = 3.0f,
                labelAnchorY = 1.0f,
                center = Offset(3.0f, 1.0f),
                radius = 20.0f
            ),
            BoundingShape.Circle(
                data = pointOf(x = 0.5f, y = 0.5f),
                labelAnchorX = 1.0f,
                labelAnchorY = 1.0f,
                center = Offset(1.0f, 1.0f),
                radius = 20.0f
            ),
            BoundingShape.Circle(
                data = pointOf(x = -0.5f, y = 0.5f),
                labelAnchorX = -1.0f,
                labelAnchorY = 1.0f,
                center = Offset(-1.0f, 1.0f),
                radius = 20.0f
            )
        )
        val result = with(lineRenderer(lineDrawer = { })) {
            RendererScope(drawScope, rendererContext).render(series)
        }
        assertEquals(expected, result)
    }
}