@Suppress("DSL_SCOPE_VIOLATION") // Until Gradle 8.1
plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.paparazzi.get().pluginId)
    id("common-config")
}

android {
    namespace = "io.github.staakk.cchart.viewtest"

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeVersion.get()
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)

    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(project(":lib"))

    testImplementation(libs.bundles.test.tools)
    testImplementation(libs.bundles.androidx.test)
    testImplementation(libs.bundles.compose.test)
}