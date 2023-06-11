@Suppress("DSL_SCOPE_VIOLATION") // Until Gradle 8.1
plugins {
    id("staakk.android.library")
    id("staakk.android.compose")
    alias(libs.plugins.dokka)
    alias(libs.plugins.versions)
    id("maven-publish")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "io.github.staakk.cchart"
}

dependencies {
    implementation(libs.bundles.androidx)
    implementation(libs.material)

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
                version = Config.versionName

                artifact(sourcesJar)
                artifact(javadocJar)
            }
        }
    }
}