import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class CommonConfigurationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("kotlin-android")

        val androidExt = target.extensions.getByName("android")
        if (androidExt is BaseExtension) {
            androidExt.apply {
                compileSdkVersion(Config.compileSdkVersion)
                defaultConfig {
                    targetSdk = Config.targetSdkVersion
                    minSdk = Config.minSdkVersion

                    versionCode = Config.version
                    versionName = Config.versionName
                }

                compileOptions {
                    isCoreLibraryDesugaringEnabled = true
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                target.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "1.8"
                        useIR = true
                    }
                }

                buildFeatures.compose = true

                composeOptions {
                    kotlinCompilerExtensionVersion = Libs.Compose.version
                }

                target.dependencies {
                    add("coreLibraryDesugaring", Libs.desugar)
                }
                packagingOptions {
                    resources.excludes += "META-INF/AL2.0"
                    resources.excludes += "META-INF/LGPL2.1"
                }
            }
        }
    }
}