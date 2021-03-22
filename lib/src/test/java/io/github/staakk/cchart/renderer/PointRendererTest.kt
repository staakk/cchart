package io.github.staakk.cchart.renderer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import io.github.staakk.cchart.data.Point
import io.github.staakk.cchart.data.Viewport
import io.github.staakk.cchart.data.pointOf
import io.github.staakk.cchart.data.seriesOf
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class PointRendererTest {

    private val circleRadius = 0.1f

    private val circleDrawer: CircleDrawer = { point, center, radius ->
        BoundingShape.Circle(
            point = point,
            labelAnchorX = center.x,
            labelAnchorY = center.y,
            center = center,
            radius = radius
        )
    }

    private val rendererContext = rendererContext(
        viewport = Viewport(minX = 0f, maxX = 1f, minY = 0f, maxY = 1f),
        canvasSize = Size(2f, 2f)
    )

    private val drawScope = mockk<DrawScope>()

    private val series = seriesOf(
        pointOf(-1, 0.5),
        pointOf(0, 0.2),
        pointOf(0.5f, 1f),
        pointOf(1f, 1f),
        pointOf(0.6f, 1.3f),
        pointOf(0.6f, -1.3f),
        pointOf(1.6f, 0.3f),
    )

    @Test
    fun `test point renderer`() {
        val expected = listOf<BoundingShape>(
            BoundingShape.Circle(
                point = Point(x = 0f, y = 0.2f, tag = null),
                labelAnchorX = 0f,
                labelAnchorY = -0.4f,
                center = Offset(0f, -0.4f),
                radius = circleRadius
            ),
            BoundingShape.Circle(
                point = Point(x = 0.5f, y = 1f, tag = null),
                labelAnchorX = 1f,
                labelAnchorY = -2f,
                center = Offset(1f, -2f),
                radius = circleRadius
            ),
            BoundingShape.Circle(
                point = Point(x = 1f, y = 1f, tag = null),
                labelAnchorX = 2f,
                labelAnchorY = -2f,
                center = Offset(2f, -2f),
                radius = circleRadius
            )
        )
        val result = with(pointRenderer(radius = circleRadius, circleDrawer = circleDrawer)) {
            drawScope.render(rendererContext, series)
        }
        assertEquals(expected, result)
    }
}