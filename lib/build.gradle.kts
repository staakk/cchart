@Suppress("DSL_SCOPE_VIOLATION") // Until Gradle 8.1
plugins {
    id("cchart.versions-convention")
    id("cchart.lib-android-convention")
    id("cchart.kotlin-convention")
    alias(libs.plugins.dokka)
    alias(libs.plugins.versions)
    id("maven-publish")
    id(libs.plugins.paparazzi.get().pluginId)
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
    implementation(libs.bundles.compose)

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