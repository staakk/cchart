plugins {
    id("cchart.versions-convention")
    id("cchart.lib-android-convention")
    id("cchart.kotlin-convention")
    alias(libs.plugins.dokka)
    alias(libs.plugins.versions)
    id("maven-publish")
    id(libs.plugins.paparazzi.get().pluginId)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "io.github.staakk.cchart"
}

dependencies {
    coreLibraryDesugaring(libs.android.desugar)
    implementation(libs.bundles.androidx)
    implementation(libs.material)

    implementation(compose.animation)
    implementation(compose.runtime)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(compose.ui)
    implementation(compose.components.uiToolingPreview)

    testImplementation(libs.bundles.compose.test)
    testImplementation(libs.bundles.test.tools)
    testImplementation(libs.bundles.androidx.test)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs())
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn(tasks.findByName("dokkaJavadoc"))
    archiveClassifier.set("javadoc")
    from(tasks["dokkaJavadoc"])
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "io.github.staakk"
                artifactId = "cchart"
                version = extra["versionName"] as String

                artifact(sourcesJar)
                artifact(javadocJar)
            }
        }
    }
}
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