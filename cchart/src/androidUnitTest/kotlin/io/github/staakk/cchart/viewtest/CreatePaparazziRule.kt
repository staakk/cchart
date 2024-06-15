package io.github.staakk.cchart.viewtest

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.HtmlReportWriter
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.SnapshotHandler
import app.cash.paparazzi.SnapshotVerifier
import com.android.ide.common.rendering.api.SessionParams.RenderingMode
import java.io.File


private val isVerifying = System.getProperty("paparazzi.test.verify")?.toBoolean() == true

fun createFullScreenPaparazziRule() = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5,
    renderingMode = RenderingMode.NORMAL,
    showSystemUi = true,
    theme = "android:Theme.Material.Light.NoActionBar",
    snapshotHandler = getHandler()
)

fun createViewPaparazziRule() = Paparazzi(
    deviceConfig = DeviceConfig.PIXEL_5.copy(softButtons = false),
    renderingMode = RenderingMode.SHRINK,
    showSystemUi = false,
    theme = "android:Theme.Material.Light.NoActionBar",
    snapshotHandler = getHandler()
)

private fun getHandler() =
    if (isVerifying) SnapshotVerifier(0.1, rootDirectory = File("src/androidUnitTest/snapshots"))
    else HtmlReportWriter(snapshotRootDirectory = File("src/androidUnitTest/snapshots"))