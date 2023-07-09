package io.github.staakk.cchart

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi

fun createPaparazziRule() = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
    theme = "android:Theme.Material.Light.NoActionBar",
)