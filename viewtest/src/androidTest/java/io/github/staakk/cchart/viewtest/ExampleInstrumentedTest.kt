package io.github.staakk.cchart.viewtest

import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.rule.GrantPermissionRule
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test

class ExampleInstrumentedTest : ScreenshotTest {

    @Rule
    @JvmField
    var permission: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @Rule
    @JvmField
    var composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun viewTest() {
        composeRule.setContent { Text("Test") }

        compareScreenshot(composeRule)
    }
}