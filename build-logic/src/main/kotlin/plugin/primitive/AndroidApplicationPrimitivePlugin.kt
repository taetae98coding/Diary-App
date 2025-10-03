package plugin.primitive

import Build
import io.github.taetae98coding.diary.gradle.androidApplicationExtension
import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidApplicationPrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("diary.primitive.kotlin.multiplatform")
                apply("com.android.application")
            }

            with(kotlinMultiplatformExtension) {
                androidTarget()
                applyDefaultHierarchyTemplate()
            }

            with(androidApplicationExtension) {
                compileSdk = Build.ANDROID_COMPILE_SDK
                defaultConfig {
                    minSdk = Build.ANDROID_MIN_SDK
                    targetSdk = Build.ANDROID_TARGET_SDK
                }
            }
        }
    }
}
