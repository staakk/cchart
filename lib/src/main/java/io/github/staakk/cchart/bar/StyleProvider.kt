package io.github.staakk.cchart.bar

import io.github.staakk.cchart.renderer.RendererPoint
import io.github.staakk.cchart.style.PrimitiveStyle

fun interface StyleProvider {

    operator fun invoke(indexInGroup: Int, rendererPoint: RendererPoint<*>): PrimitiveStyle
}