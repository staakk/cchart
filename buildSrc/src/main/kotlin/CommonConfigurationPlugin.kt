import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
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
                    targetSdkVersion(Config.targetSdkVersion)
                    minSdkVersion(Config.minSdkVersion)

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

                val proguardFile = "proguard-rules.pro"
                when (this) {
                    is LibraryExtension -> defaultConfig {
                        consumerProguardFiles(proguardFile)
                    }
                    is AppExtension -> buildTypes {
                        getByName("release") {
                            isMinifyEnabled = true
                            isShrinkResources = true
                            proguardFiles(
                                getDefaultProguardFile("proguard-android-optimize.txt"),
                                proguardFile
                            )
                        }
                    }
                }

                target.dependencies {
                    add("coreLibraryDesugaring", Libs.desugar)
                }

                packagingOptions {
                    resources.excludes += "META-INF/**"
                }
            }
        }
    }
}