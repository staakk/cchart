import io.github.staakk.cchart.configureAndroid
import org.gradle.kotlin.dsl.extra

plugins {
    id("cchart.kotlin-convention")
    id("cchart.versions-convention")
    id("com.android.application")
}

android {
    configureAndroid(project)

    defaultConfig {
        targetSdk = extra["targetSdkVersion"] as Int

        versionCode = extra["version"] as Int
        versionName = extra["versionName"] as String
    }
}