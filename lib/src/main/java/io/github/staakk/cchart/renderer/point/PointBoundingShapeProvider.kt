package io.github.staakk.cchart.renderer.point

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.renderer.BoundingShape
import io.github.staakk.cchart.renderer.BoundingShapeProvider
import io.github.staakk.cchart.renderer.RendererPoint

class SimplePointBoundingShapeProvider(
    private val size: Size,
) : BoundingShapeProvider {
    override fun provide(rendererPoints: List<RendererPoint<*>>): List<BoundingShape> {
        return rendererPoints.map {
            BoundingShape.Circle(
                data = it.data,
                labelAnchorX = it.x,
                labelAnchorY = it.y,
                center = it.toOffset(),
                radius = size.width / 2
            )
        }
    }
}