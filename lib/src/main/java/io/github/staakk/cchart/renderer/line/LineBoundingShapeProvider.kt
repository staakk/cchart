package io.github.staakk.cchart.renderer.line

import io.github.staakk.cchart.renderer.BoundingShape
import io.github.staakk.cchart.renderer.BoundingShapeProvider
import io.github.staakk.cchart.renderer.RendererPoint

class LineBoundingShapeProvider(
    private val radius: Float = 20f,
) : BoundingShapeProvider {

    override fun provide(index: Int, rendererPoints: List<RendererPoint<*>>): List<BoundingShape> {
        val it = rendererPoints[index]
        return listOf(
            BoundingShape.Circle(
                data = it.data,
                labelAnchorX = it.x,
                labelAnchorY = it.y,
                center = it.toOffset(),
                radius = radius
            )
        )
    }
}