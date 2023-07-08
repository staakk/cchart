package io.github.staakk.cchart.renderer

fun interface Drawer {

    fun RendererScope.draw(point: RendererPoint<*>)
}