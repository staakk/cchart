package io.github.staakk.cchart.grid

fun interface GridLinesProvider {

    /**
     * Provides grid lines position on the chart.
     *
     * @param min Minimum value to provide the line for.
     * @param max Maximum value to provide the line for.
     * @return Positions of the grid lines
     */
    fun provide(min: Float, max: Float): List<Float>
}

/**
 * Provides grid line position for each integer number.
 */
object IntGridLinesProvider : GridLinesProvider {

    override fun provide(min: Float, max: Float) = (min.toInt()..max.toInt()).map(Int::toFloat)
}

object GridLinesProviders {

    fun intGrid() = IntGridLinesProvider

    fun fraction(value: Int) = GridLinesProvider { min, max ->
        (min.toInt()..max.toInt())
            .flatMap { intValue -> (1..value).map { intValue + it.toFloat() / value } }
    }

    fun multiple(value: Int) = GridLinesProvider { min, max ->
        (min.toInt()..max.toInt()).filter { it % value != 0 }.map(Int::toFloat)
    }
}