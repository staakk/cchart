package io.github.staakk.composechart.axis

fun interface LabelsProvider {
    fun createLabels(min: Float, max: Float): List<Pair<String, Float>>
}

val INT_LABELS_PROVIDER = LabelsProvider { min: Float, max: Float ->
    (min.toInt()..(max.toInt() + 1)).map { "$it" to it.toFloat()}
}