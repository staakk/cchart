import org.gradle.api.artifacts.dsl.DependencyHandler

const val kotlinVersion = "1.8.0"

object Libs {
    const val desugar = "com.android.tools:desugar_jdk_libs:1.2.2"
    const val junit = "junit:junit:4.13.2"
    const val mockk = "io.mockk:mockk:1.12.0"
    const val hamcrest = "org.hamcrest:hamcrest:2.2"
    const val material = "com.google.android.material:material:1.4.0"

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.6.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.1"

        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
        const val rules = "androidx.test:rules:1.3.0"

        val main = listOf(core, appcompat)
        val androidTest = listOf(extJunit, espresso, rules)
    }

    object Compose {
        const val version = "1.4.1"

        const val animation = "androidx.compose.animation:animation:$version"
        const val foundation = "androidx.compose.foundation:foundation:$version"
        const val foundationLayout = "androidx.compose.foundation:foundation-layout:$version"
        const val materialIcons =
            "androidx.compose.material:material-icons-extended:$version"
        const val material = "androidx.compose.material:material:$version"
        const val runtime = "androidx.compose.runtime:runtime:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val ui = "androidx.compose.ui:ui:$version"
        const val uiUtil = "androidx.compose.ui:ui-util:$version"
        const val activity = "androidx.activity:activity-compose:$version"

        const val uiTest = "androidx.compose.ui:ui-test:$version"
        const val uiTestJUnit = "androidx.compose.ui:ui-test-junit4:$version"

        val main = listOf(
            animation,
            foundation,
            foundationLayout,
            materialIcons,
            material,
            runtime,
            uiTooling,
            ui,
            uiUtil,
            activity
        )

        val androidTest = listOf(
            uiTest,
            uiTestJUnit
        )
    }
}

fun DependencyHandler.implementations(dependencies: List<String>) {
    dependencies.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.androidTestImplementations(dependencies: List<String>) {
    dependencies.forEach {
        add("androidTestImplementation", it)
    }
}

