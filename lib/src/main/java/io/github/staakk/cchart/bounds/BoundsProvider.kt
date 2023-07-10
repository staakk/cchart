package io.github.staakk.cchart.bounds

import io.github.staakk.cchart.renderer.RendererPoint

val NoBounds = BoundsProvider { listOf(Bounds.None) }

fun interface BoundsProvider {

    fun provide(rendererPoints: List<RendererPoint<*>>): List<Bounds>
}