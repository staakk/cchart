package io.github.staakk.cchart.renderer

val NoBoundingShape = BoundingShapeProvider { _, _ -> BoundingShape.None }

fun interface BoundingShapeProvider {

    fun provide(index: Int, rendererPoints: List<RendererPoint<*>>): BoundingShape
}