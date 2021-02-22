package io.github.staakk.cchart.util

import android.graphics.Paint
import kotlin.math.abs

internal val Paint.FontMetrics.lineHeight
    get() = abs(top) + abs(bottom)