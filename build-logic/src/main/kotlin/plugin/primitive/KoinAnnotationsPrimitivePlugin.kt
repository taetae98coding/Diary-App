package plugin.primitive

import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import io.github.taetae98coding.diary.gradle.library
import io.github.taetae98coding.diary.gradle.libs
import io.github.taetae98coding.diary.gradle.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KoinAnnotationsPrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.libs

        with(target) {
            with(kotlinMultiplatformExtension) {
                sourceSets {
                    commonMain {
                        dependencies {
                            implementation(project.dependencies.platform(libs.library("koin-annotations-bom")))
                            implementation(libs.library("koin-annotations"))
                        }
                    }
                }
            }
        }
    }
}
