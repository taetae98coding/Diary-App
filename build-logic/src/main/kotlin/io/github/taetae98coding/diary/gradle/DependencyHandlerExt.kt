package io.github.taetae98coding.diary.gradle

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider

public fun DependencyHandler.kspAll(
    dependencyNotation: Provider<MinimalExternalModuleDependency>,
) {
//    add("kspCommonMainMetadata", dependencyNotation)
    add("kspJvm", dependencyNotation)
    add("kspAndroid", dependencyNotation)
//    add("kspIosX64", dependencyNotation)
    add("kspIosArm64", dependencyNotation)
    add("kspIosSimulatorArm64", dependencyNotation)
}
