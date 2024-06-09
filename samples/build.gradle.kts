import io.github.staakk.cchart.configureAndroid

plugins {
    id("com.android.application")
    id("cchart.versions-convention")
    id("cchart.kotlin-convention")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    configureAndroid(project)

    val basePackage = "io.github.staakk.cchart.samples"
    defaultConfig {
        targetSdk = extra["targetSdkVersion"] as Int

        versionCode = extra["version"] as Int
        versionName = extra["versionName"] as String
        applicationId = basePackage
    }

    namespace = basePackage
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)
    implementation(libs.bundles.androidx)

    implementation(project(":lib"))

    implementation(libs.androidx.activity)
    implementation(compose.animation)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.components.uiToolingPreview)

    testImplementation(libs.bundles.compose.test)
}