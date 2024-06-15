
plugins {
    id("cchart.app-android-convention")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    val basePackage = "io.github.staakk.cchart.samples"
    defaultConfig {
        applicationId = basePackage
    }
    namespace = basePackage

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)
    implementation(libs.bundles.androidx)
    implementation(libs.material)

//    implementation(project(":cchart"))
    implementation("io.github.staakk:cchart:1.0.0")
    implementation(libs.androidx.activity)
    implementation(compose.animation)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.components.uiToolingPreview)
}