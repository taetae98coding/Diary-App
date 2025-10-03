package plugin.primitive

import io.github.taetae98coding.diary.gradle.kotlinMultiplatformExtension
import io.github.taetae98coding.diary.gradle.sourceSets
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal class ComposePrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }

            extensions.configure<ComposeCompilerGradlePluginExtension> {
                metricsDestination.set(rootDir.resolve("build/compose/metrics"))
                reportsDestination.set(rootDir.resolve("build/compose/reports"))
                stabilityConfigurationFiles.add { rootDir.resolve("compose-stability-config.txt") }
            }
        }
    }
}
