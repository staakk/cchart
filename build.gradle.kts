buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("app.cash.paparazzi:paparazzi-gradle-plugin:1.3.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx/")
        @Suppress("JcenterRepositoryObsolete", "DEPRECATION") // Required by dokka plugin
        jcenter()
    }
}

tasks.register("clean").configure {
    delete("build")
}