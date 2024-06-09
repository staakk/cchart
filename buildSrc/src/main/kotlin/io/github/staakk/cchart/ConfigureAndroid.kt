package io.github.staakk.cchart

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

fun CommonExtension<*, *, *, *, *>.configureAndroid(project: Project, desugar: Boolean = true) {
    compileSdk = project.extra["compileSdkVersion"] as Int
    defaultConfig.minSdk = project.extra["minSdkVersion"] as Int

    compileOptions {
        isCoreLibraryDesugaringEnabled = desugar
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    // Find a better place for this
    packaging.resources.excludes += listOf(
        "META-INF/AL2.0",
        "META-INF/LGPL2.1",
    )
}