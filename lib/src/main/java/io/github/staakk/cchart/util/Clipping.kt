package io.github.staakk.cchart.util

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect

internal fun DrawScope.applyExtendedClipping(
    enabled: Boolean,
    extension: Size,
    f: DrawScope.() -> Unit,
) {
    if (!enabled) return f()
    clipRect(
        top = size.height + extension.height,
        right = size.width + extension.width,
        bottom = -extension.height,
        left = -extension.width,
        block = f,
    )
}