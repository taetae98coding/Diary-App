package plugin.primitive

import io.github.taetae98coding.diary.gradle.bundle
import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import io.github.taetae98coding.diary.gradle.library
import io.github.taetae98coding.diary.gradle.libs
import io.github.taetae98coding.diary.gradle.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

internal class KotestPrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.libs
        with(target) {
            with(kotlinMultiplatformExtension) {
                sourceSets {
                    commonTest {
                        dependencies {
                            implementation(libs.bundle("kotest"))
                        }
                    }

                    jvmTest {
                        dependencies {
                            implementation(libs.library("kotest-junit"))
                            implementation(libs.library("fixture-monkey"))
                            implementation(libs.library("mockk"))
                        }
                    }
                }
            }

            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}
