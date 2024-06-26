plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly(gradleApi())

    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(kotlin("android-extensions"))
}
