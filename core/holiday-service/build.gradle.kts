import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
    id("diary.primitive.kotest")
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.holiday.service"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.core.coroutines)
                implementation(projects.core.ktorClient)

                implementation(libs.kotlinx.datetime)

                implementation(ktorLibs.client.core)
                implementation(ktorLibs.client.contentNegotiation)
                implementation(ktorLibs.serialization.kotlinx.json)
            }
        }

        commonTest {
            dependencies {
                implementation(projects.testing.ktor)
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}
