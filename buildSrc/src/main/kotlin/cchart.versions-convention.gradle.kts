import org.gradle.kotlin.dsl.extra

project.extra.apply {
    set("minSdkVersion", 21)
    set("compileSdkVersion", 33)
    set("targetSdkVersion", 33)
    set("version", 4)
    set("versionName", "1.0.0")
}