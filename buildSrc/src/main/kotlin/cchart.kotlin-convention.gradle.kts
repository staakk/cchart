import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-android")
}

tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}