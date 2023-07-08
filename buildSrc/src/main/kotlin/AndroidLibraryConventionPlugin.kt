import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import io.github.staakk.cchart.configureKotlinAndroid

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("kotlin-android")
        }

        extensions.configure(LibraryExtension::class.java) {
            configureKotlinAndroid(this)
        }
    }

}