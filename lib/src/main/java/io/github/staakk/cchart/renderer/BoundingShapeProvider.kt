package io.github.staakk.cchart.renderer

val NoBoundingShape = BoundingShapeProvider { listOf(BoundingShape.None) }

fun interface BoundingShapeProvider {

    fun provide(rendererPoints: List<RendererPoint<*>>): List<BoundingShape>
}