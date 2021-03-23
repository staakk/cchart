package io.github.staakk.cchart.label

fun interface LabelsProvider {

    fun provide(min: Float, max: Float): List<Pair<String, Float>>
}

object IntLabelsProvider : LabelsProvider {
    override fun provide(min: Float, max: Float): List<Pair<String, Float>> {
        require(max > min) { "Max should be larger than min. min: $min, max: $max" }
        return (min.toInt()..(max.toInt() + 1)).map { "$it" to it.toFloat() }
    }
}