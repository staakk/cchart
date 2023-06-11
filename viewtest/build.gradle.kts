@Suppress("DSL_SCOPE_VIOLATION") // Until Gradle 8.1
plugins {
    id("staakk.android.library")
    id("staakk.android.compose")
    id(libs.plugins.paparazzi.get().pluginId)
}

android {
    namespace = "io.github.staakk.cchart.viewtest"
}

dependencies {
    implementation(libs.bundles.androidx)
    implementation(project(":lib"))

    testImplementation(libs.bundles.test.tools)
    testImplementation(libs.bundles.androidx.test)
}