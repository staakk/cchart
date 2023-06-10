plugins {
    id("com.android.application")
    id("common-config")
    id("shot")
}

android {
    defaultConfig {
        applicationId = "io.github.staakk.cchart.viewtest"

        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
    }
    namespace = "io.github.staakk.cchart.viewtest"
}

dependencies {
    implementations(Libs.AndroidX.main)
    implementations(Libs.Compose.main)
    implementation(project(":lib"))

    testImplementation(Libs.junit)

    androidTestImplementations(Libs.AndroidX.androidTest)
    androidTestImplementations(Libs.Compose.androidTest)
}