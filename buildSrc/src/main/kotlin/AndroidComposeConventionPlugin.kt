import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        extensions.configure(CommonExtension::class.java) {
            buildFeatures.compose = true
            composeOptions {
                kotlinCompilerExtensionVersion = libs
                    .findVersion("composeVersion")
                    .get()
                    .toString()
            }

            dependencies {
                "implementation"(libs.findBundle("compose").get())
                "testImplementation"(libs.findBundle("compose.test").get())
            }
        }
    }
}