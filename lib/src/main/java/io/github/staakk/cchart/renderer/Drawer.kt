package io.github.staakk.cchart.renderer

fun interface Drawer {

    fun RendererScope.draw(index: Int, rendererPoints: List<RendererPoint<*>>)
}