package io.github.staakk.cchart.samples

import io.github.staakk.cchart.data.pointOf
import kotlin.math.sin

object SampleData {

    val series = sequence {
        var step = 0.0f
        while(true) {
            yield(pointOf(step, 1.5f * sin(1.2f * step) + 1.2f * step))
            step += 0.2f
        }
    }
}