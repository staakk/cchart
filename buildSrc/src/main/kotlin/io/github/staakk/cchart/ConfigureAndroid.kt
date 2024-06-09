package io.github.staakk.cchart

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

fun CommonExtension<*, *, *, *>.configureAndroid(project: Project) {
    compileSdk = project.extra["compileSdkVersion"] as Int
    defaultConfig.minSdk = project.extra["minSdkVersion"] as Int

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Find a better place for this
    packagingOptions.resources.excludes += listOf(
        "META-INF/AL2.0",
        "META-INF/LGPL2.1",
    )

    buildFeatures.compose = true

    composeOptions {
        kotlinCompilerExtensionVersion = project
            .libs
            .findVersion("composeVersion")
            .get()
            .toString()
    }
}