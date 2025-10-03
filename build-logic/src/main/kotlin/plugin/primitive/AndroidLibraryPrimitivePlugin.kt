package plugin.primitive

import Build
import com.android.build.api.dsl.androidLibrary
import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class AndroidLibraryPrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("diary.primitive.kotlin.multiplatform")
                apply("com.android.kotlin.multiplatform.library")
            }

            with(kotlinMultiplatformExtension) {
                androidLibrary {
                    compileSdk = Build.ANDROID_COMPILE_SDK
                    minSdk = Build.ANDROID_MIN_SDK
                }
                applyDefaultHierarchyTemplate()
            }
        }
    }
}
