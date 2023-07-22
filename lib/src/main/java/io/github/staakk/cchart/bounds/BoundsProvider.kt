package io.github.staakk.cchart.bounds

import io.github.staakk.cchart.renderer.RendererPoint

fun interface BoundsProvider {

    fun provide(rendererPoints: List<RendererPoint<*>>): List<Bounds>

    companion object {
        val NoBounds = BoundsProvider { listOf(Bounds.None) }
    }
}