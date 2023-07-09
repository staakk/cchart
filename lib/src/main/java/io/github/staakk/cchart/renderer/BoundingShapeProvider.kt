package io.github.staakk.cchart.renderer

val NoBoundingShape = BoundingShapeProvider { _, _ -> listOf(BoundingShape.None) }

fun interface BoundingShapeProvider {

    fun provide(index: Int, rendererPoints: List<RendererPoint<*>>): List<BoundingShape>
}