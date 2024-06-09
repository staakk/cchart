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

gradlePlugin {
    plugins {
        register("library") {
            id = "staakk.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("compose") {
            id = "staakk.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}

