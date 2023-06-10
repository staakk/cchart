package io.github.staakk.cchart.viewtest

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi

fun createPaparazziRule() = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
    theme = "android:Theme.Material.Light.NoActionBar",
)