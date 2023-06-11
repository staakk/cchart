plugins {
    id("staakk.android.application")
    id("staakk.android.compose")
}

android {
    val basePackage = "io.github.staakk.cchart.samples"
    defaultConfig {
        applicationId = basePackage
    }

    namespace = basePackage
}

dependencies {
    implementation(libs.bundles.androidx)
    implementation(project(":lib"))
}