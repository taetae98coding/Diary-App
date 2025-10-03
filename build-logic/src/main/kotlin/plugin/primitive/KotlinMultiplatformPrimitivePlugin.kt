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
