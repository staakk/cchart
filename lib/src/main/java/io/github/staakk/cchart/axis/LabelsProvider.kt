package io.github.staakk.cchart.axis

fun interface LabelsProvider {
    fun createLabels(min: Float, max: Float): List<Pair<String, Float>>
}

val INT_LABELS_PROVIDER = LabelsProvider { min: Float, max: Float ->
    require(max > min) { "Max should be larger than min. min: $min, max: $max" }
    (min.toInt()..(max.toInt() + 1)).map { "$it" to it.toFloat()}
}