package io.github.taetae98coding.diary.gradle

import com.android.build.api.dsl.ApplicationExtension
import com.google.devtools.ksp.gradle.KspExtension
import java.util.Properties
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

private val Project.versionCatalogsExtension: VersionCatalogsExtension
    get() = extensions.getByType<VersionCatalogsExtension>()

internal val Project.libs: VersionCatalog
    get() = versionCatalogsExtension.named("libs")

internal val Project.kotlinMultiplatformExtension: KotlinMultiplatformExtension
    get() = extensions.getByType()

internal val Project.androidApplicationExtension: ApplicationExtension
    get() = extensions.getByType()

internal val Project.ksp: KspExtension
    get() = extensions.getByType()

public fun Project.getLocalProperties(): Properties {
    val file = project.rootProject.file("local.properties")

    return Properties().apply {
        file.inputStream()
            .buffered()
            .use { load(it) }
    }
}
