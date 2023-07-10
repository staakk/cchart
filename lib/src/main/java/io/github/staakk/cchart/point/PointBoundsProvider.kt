package io.github.staakk.cchart.point

import androidx.compose.ui.geometry.Size
import io.github.staakk.cchart.bounds.Bounds
import io.github.staakk.cchart.bounds.BoundsProvider
import io.github.staakk.cchart.renderer.RendererPoint

class PointBoundsProvider(
    private val size: Size,
) : BoundsProvider {
    override fun provide(rendererPoints: List<RendererPoint<*>>): List<Bounds> {
        return rendererPoints.map {
            Bounds.Circle(
                data = it.data,
                labelAnchorX = it.x,
                labelAnchorY = it.y,
                center = it.toOffset(),
                radius = size.width / 2
            )
        }
    }
}