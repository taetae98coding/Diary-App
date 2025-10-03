package plugin.primitive

import io.github.taetae98coding.diary.gradle.ksp
import io.github.taetae98coding.diary.gradle.kspAll
import io.github.taetae98coding.diary.gradle.library
import io.github.taetae98coding.diary.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

internal class KoinCompilerPrimitivePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.libs

        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            with(dependencies) {
                kspAll(platform(libs.library("koin-annotations-bom")))
                kspAll(libs.library("koin-annotations-compiler"))
            }

            with(ksp) {
                arg("KOIN_DEFAULT_MODULE", "false")
            }
        }
    }
}
