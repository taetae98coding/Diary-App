package plugin.convention

import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import io.github.taetae98coding.diary.gradle.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class DataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("diary.primitive.android.library")
                apply("diary.primitive.koin.core")
                apply("diary.primitive.koin.annotations")
                apply("diary.primitive.koin.compiler")
                apply("diary.primitive.kotest")
            }

            with(kotlinMultiplatformExtension) {
                sourceSets {
                    commonMain {
                        dependencies {
                            implementation(project(":core:mapper"))
                        }
                    }
                }
            }
        }
    }
}
