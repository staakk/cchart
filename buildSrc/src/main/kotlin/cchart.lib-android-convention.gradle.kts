import io.github.staakk.cchart.configureAndroid

plugins {
    id("com.android.library")
}

android {
    configureAndroid(project, desugar = false)
}