package io.github.staakk.cchart.renderer.point

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.renderer.BoundingShape
import io.github.staakk.cchart.renderer.BoundingShapeProvider
import io.github.staakk.cchart.renderer.RendererPoint

class SimplePointBoundingShapeProvider(
    private val size: Size,
) : BoundingShapeProvider {
    override fun provide(index: Int, rendererPoints: List<RendererPoint<*>>): List<BoundingShape> {
        val point = rendererPoints[index]
        return listOf(
            BoundingShape.Circle(
                data = point.data,
                labelAnchorX = point.x,
                labelAnchorY = point.y,
                center = point.toOffset(),
                radius = size.width / 2
            )
        )
    }
}