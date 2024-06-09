import org.gradle.kotlin.dsl.extra

project.extra.apply {
    set("minSdkVersion", 21)
    set("compileSdkVersion", 34)
    set("targetSdkVersion", 34)
    set("version", 4)
    set("versionName", "1.0.0")
}