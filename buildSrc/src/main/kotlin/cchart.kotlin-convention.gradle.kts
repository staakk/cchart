import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-android")
}

tasks.withType(KotlinCompile::class.java).configureEach {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}