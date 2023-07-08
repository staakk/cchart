import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import io.github.staakk.cchart.configureKotlinAndroid

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("kotlin-android")
        }

        extensions.configure(ApplicationExtension::class.java) {
            configureKotlinAndroid(this)
            defaultConfig {
                targetSdk = Config.targetSdkVersion

                versionCode = Config.version
                versionName = Config.versionName
            }
        }
    }
}