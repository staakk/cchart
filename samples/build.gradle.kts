import io.github.staakk.cchart.configureAndroid

plugins {
    id("com.android.application")
    id("cchart.versions-convention")
    id("cchart.kotlin-convention")
}

android {
    val basePackage = "io.github.staakk.cchart.samples"
    defaultConfig {
        targetSdk = extra["targetSdkVersion"] as Int

        versionCode = extra["version"] as Int
        versionName = extra["versionName"] as String
        applicationId = basePackage
    }

    namespace = basePackage

    configureAndroid(project)
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)
    implementation(libs.bundles.androidx)
    implementation(project(":lib"))
    implementation(libs.bundles.compose)
    testImplementation(libs.bundles.compose.test)
}