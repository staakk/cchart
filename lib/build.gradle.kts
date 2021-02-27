plugins {
    id("com.android.library")
    id("common-config")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.4.20"
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementations(Libs.AndroidX.main)
    implementations(Libs.Compose.main)
    implementation(Libs.material)

    testImplementation(Libs.junit)

    androidTestImplementations(Libs.AndroidX.androidTest)
    androidTestImplementations(Libs.Compose.androidTest)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.getByName("main").java.srcDirs)
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