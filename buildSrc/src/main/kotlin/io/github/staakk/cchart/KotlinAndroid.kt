package io.github.staakk.cchart

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    commonExtension.apply {
        compileSdk = Config.compileSdkVersion
        defaultConfig.minSdk = Config.minSdkVersion

        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        dependencies {
            "coreLibraryDesugaring"(libs.findLibrary("android.desugar").get())
        }

        tasks.withType(KotlinCompile::class.java).configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }

        // Find a better place for this
        packagingOptions {
            resources.excludes += "META-INF/AL2.0"
            resources.excludes += "META-INF/LGPL2.1"
        }
    }
}