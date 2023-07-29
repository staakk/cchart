package io.github.staakk.cchart.viewtest

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams.RenderingMode

fun createFullScreenPaparazziRule() = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
    renderingMode = RenderingMode.NORMAL,
    showSystemUi = true,
    theme = "android:Theme.Material.Light.NoActionBar",
)

fun createViewPaparazziRule() = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false),
    renderingMode = RenderingMode.SHRINK,
    showSystemUi = false,
    theme = "android:Theme.Material.Light.NoActionBar",
)