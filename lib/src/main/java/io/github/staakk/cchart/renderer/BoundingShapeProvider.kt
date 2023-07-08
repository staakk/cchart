package io.github.staakk.cchart.renderer

val NoBoundingShape = BoundingShapeProvider { BoundingShape.None }

fun interface BoundingShapeProvider {

    fun provide(point: RendererPoint<*>): BoundingShape
}