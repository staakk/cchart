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

    implementation("com.android.tools.build:gradle:7.0.0-alpha15")
    implementation(kotlin("gradle-plugin", "1.4.32"))
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

