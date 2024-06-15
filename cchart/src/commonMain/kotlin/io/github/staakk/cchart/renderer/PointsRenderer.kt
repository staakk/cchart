package io.github.staakk.cchart.renderer

fun interface PointsRenderer {

    fun RendererScope.draw(rendererPoints: List<RendererPoint<*>>)
}