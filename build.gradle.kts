buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.paparazzi.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        @Suppress("JcenterRepositoryObsolete", "DEPRECATION") // Required by dokka plugin
        jcenter()
    }
}

tasks.register("clean").configure {
    delete("build")
}