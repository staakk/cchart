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

    implementation("com.android.tools.build:gradle:7.0.2")
    implementation(kotlin("gradle-plugin", "1.5.30"))
    implementation(kotlin("android-extensions"))
}

gradlePlugin {
    plugins {
        register("common-config") {
            id = "common-config"
            implementationClass = "CommonConfigurationPlugin"
        }
    }
}

