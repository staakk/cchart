package io.github.staakk.cchart.renderer.grid

fun interface GridProvider {

    /**
     * Provides grid lines position on the chart.
     *
     * @param min Minimum value to provide the line for.
     * @param max Maximum value to provide the line for.
     * @return Positions of the grid lines
     */
    fun provide(min: Float, max: Float): List<Float>
}

object GridLinesProviders {

    val intGrid = GridProvider { min, max ->
        (min.toInt()..max.toInt()).map(Int::toFloat)
    }

    fun multiple(value: Float) = GridProvider { min, max ->
        var current = min - min % value
        val result = mutableListOf<Float>()
        while (current <= max) {
            result += current
            current += value
        }
        result
    }
}