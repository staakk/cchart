@Suppress("DSL_SCOPE_VIOLATION") // Until Gradle 8.1
plugins {
    id(libs.plugins.android.application.get().pluginId)
    id("common-config")
}

android {
    val basePackage = "io.github.staakk.cchart.samples"
    defaultConfig {
        applicationId = basePackage
    }

    namespace = basePackage

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeVersion.get()
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)

    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(project(":lib"))
}