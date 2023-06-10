plugins {
    id("com.android.library")
    id("common-config")
    id("app.cash.paparazzi")
}

android {
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