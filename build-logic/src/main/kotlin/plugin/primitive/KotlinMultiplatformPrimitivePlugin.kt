package plugin.primitive

import Build
import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import io.github.taetae98coding.diary.gradle.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KotlinMultiplatformPrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
            }

            with(kotlinMultiplatformExtension) {
                jvmToolchain(Build.JDK_VERSION)
                explicitApi()

                iosArm64()
                iosSimulatorArm64()
                jvm()
                applyDefaultHierarchyTemplate()

                sourceSets {
                    all {
                        languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")

                        languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                        languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
                        languageSettings.optIn("androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi")

                        languageSettings.optIn("androidx.paging.ExperimentalPagingApi")

                        languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
                        languageSettings.optIn("kotlin.time.ExperimentalTime")

                        languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
                        languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                    }

                    val commonMain = commonMain.get()
                    val androidMain = androidMain.get()
                    val iosMain = iosMain.get()
                    val jvmMain = jvmMain.get()

                    val nonAndroidMain = create("nonAndroidMain")
                    nonAndroidMain.dependsOn(commonMain)
                    iosMain.dependsOn(nonAndroidMain)
                    jvmMain.dependsOn(nonAndroidMain)

                    val nonIosMain = create("nonIosMain")
                    nonIosMain.dependsOn(commonMain)
                    androidMain.dependsOn(nonIosMain)
                    jvmMain.dependsOn(nonIosMain)
                }

                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }
}
