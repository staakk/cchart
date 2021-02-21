package io.github.staakk.cchart.grid

fun interface GridLinesProvider {

    fun provide(min: Float, max: Float): List<Float>
}

object IntGridLinesProvider : GridLinesProvider {

    override fun provide(min: Float, max: Float) = (min.toInt()..max.toInt()).map { it.toFloat() }
}