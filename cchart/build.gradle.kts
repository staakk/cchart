import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id(libs.plugins.kotlinMultiplatform.get().pluginId)
    id("cchart.versions-convention")
    id("cchart.lib-android-convention")
    alias(libs.plugins.versions)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
    id("maven-publish")
    id(libs.plugins.paparazzi.get().pluginId)
}

group = "io.github.staakk"
version = extra["versionName"] as String

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    jvm()

    sourceSets {
        val androidUnitTest by getting

        commonMain.dependencies {
            implementation(compose.animation)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
        }

        commonTest.dependencies {
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }

        androidUnitTest.dependencies {
            implementation(libs.bundles.test.tools)
            implementation(libs.bundles.androidx.test)
        }
    }
}

android {
    namespace = "io.github.staakk.cchart"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

//val sourcesJar by tasks.creating(Jar::class) {
//    archiveClassifier.set("sources")
//    from(android.sourceSets.getByName("main").java.srcDirs())
//}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn(tasks.findByName("dokkaJavadoc"))
    archiveClassifier.set("javadoc")
    from(tasks["dokkaJavadoc"])
}

//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("cchartRelease") {
//                from(components["kotlin"])
//
//                groupId = "io.github.staakk"
//                artifactId = "cchart"
//                version = extra["versionName"] as String
//
//                artifact(tasks.findByName("sourcesJar"))
//                artifact(javadocJar)
//            }
//        }
//    }
//}

tasks.dokkaHtml {
    moduleName.set(project.rootProject.name)
    moduleVersion.set(project.version.toString())
    failOnWarning.set(false)
    dokkaSourceSets {
        configureEach {
            samples.from("$projectDir/src/test/java")
        }
    }
}