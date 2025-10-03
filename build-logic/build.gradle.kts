plugins {
    `kotlin-dsl`
}

kotlin {
    explicitApi()
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.gradle.kotlin)
    compileOnly(libs.gradle.ksp)
    compileOnly(libs.gradle.android)
    compileOnly(libs.gradle.compose)
    compileOnly(libs.gradle.compose.compiler)
    compileOnly(libs.gradle.room)
}

gradlePlugin {
    plugins {
        register("diary.primitive.kotlin.multiplatform") {
            id = "diary.primitive.kotlin.multiplatform"
            implementationClass = "plugin.primitive.KotlinMultiplatformPrimitivePlugin"
        }

        register("diary.primitive.android.library") {
            id = "diary.primitive.android.library"
            implementationClass = "plugin.primitive.AndroidLibraryPrimitivePlugin"
        }

        register("diary.primitive.android.application") {
            id = "diary.primitive.android.application"
            implementationClass = "plugin.primitive.AndroidApplicationPrimitivePlugin"
        }

        register("diary.primitive.compose") {
            id = "diary.primitive.compose"
            implementationClass = "plugin.primitive.ComposePrimitivePlugin"
        }

        register("diary.primitive.koin.core") {
            id = "diary.primitive.koin.core"
            implementationClass = "plugin.primitive.KoinCorePrimitivePlugin"
        }

        register("diary.primitive.koin.annotations") {
            id = "diary.primitive.koin.annotations"
            implementationClass = "plugin.primitive.KoinAnnotationsPrimitivePlugin"
        }

        register("diary.primitive.koin.compiler") {
            id = "diary.primitive.koin.compiler"
            implementationClass = "plugin.primitive.KoinCompilerPrimitivePlugin"
        }

        register("diary.primitive.kotest") {
            id = "diary.primitive.kotest"
            implementationClass = "plugin.primitive.KotestPrimitivePlugin"
        }

        register("diary.primitive.ksp.common") {
            id = "diary.primitive.ksp.common"
            implementationClass = "plugin.primitive.KspCommonPrimitivePlugin"
        }

        register("diary.convention.data") {
            id = "diary.convention.data"
            implementationClass = "plugin.convention.DataConventionPlugin"
        }

        register("diary.convention.domain") {
            id = "diary.convention.domain"
            implementationClass = "plugin.convention.DomainConventionPlugin"
        }

        register("diary.convention.feature") {
            id = "diary.convention.feature"
            implementationClass = "plugin.convention.FeatureConventionPlugin"
        }
    }
}
