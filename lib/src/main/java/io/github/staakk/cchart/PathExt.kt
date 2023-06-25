package io.github.staakk.cchart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

internal fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
internal fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)