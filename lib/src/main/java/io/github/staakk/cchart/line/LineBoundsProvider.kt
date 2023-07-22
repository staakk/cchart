package io.github.staakk.cchart.line

import io.github.staakk.cchart.bounds.Bounds
import io.github.staakk.cchart.bounds.BoundsProvider
import io.github.staakk.cchart.renderer.RendererPoint

class LineBoundsProvider(
    private val radius: Float = 20f,
) : BoundsProvider {

    override fun provide(rendererPoints: List<RendererPoint<*>>): List<Bounds> {
        return rendererPoints.map {
            Bounds.Circle(
                point = it.point,
                labelAnchorX = it.x,
                labelAnchorY = it.y,
                center = it.toOffset(),
                radius = radius
            )
        }
    }
}