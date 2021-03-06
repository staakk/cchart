plugins {
    id("com.android.application")
    id("common-config")
}

android {
    defaultConfig {
        applicationId = "io.github.staakk.cchart.app"
    }
}

dependencies {
    implementations(Libs.AndroidX.main)
    implementations(Libs.Compose.main)
    implementation(project(":lib"))
}