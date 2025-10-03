package plugin.convention

import io.github.taetae98coding.diary.gradle.compose
import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import io.github.taetae98coding.diary.gradle.library
import io.github.taetae98coding.diary.gradle.libs
import io.github.taetae98coding.diary.gradle.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.libs

        with(target) {
            with(pluginManager) {
                apply("diary.primitive.android.library")
                apply("diary.primitive.compose")
                apply("diary.primitive.koin.annotations")
                apply("diary.primitive.koin.compiler")
            }

            with(kotlinMultiplatformExtension) {
                sourceSets {
                    commonMain {
                        dependencies {
                            implementation(project(":compose:core"))
                            implementation(project(":core:logger"))
                            implementation(project(":core:navigation"))
                            implementation(project(":library:kotlinx-coroutines-core"))
                            implementation(project(":library:lifecycle-common"))

                            implementation(compose.preview)

                            implementation(libs.library("kotlinx-datetime"))

                            implementation(libs.library("lifecycle-runtime-compose"))

                            implementation(libs.library("androidx-navigation3-runtime"))
                            implementation(libs.library("navigation3-ui"))

                            implementation(project.dependencies.platform(libs.library("koin-bom")))
                            implementation(libs.library("koin-compose-viewmodel-navigation"))
                        }
                    }
                }
            }
        }
    }
}
