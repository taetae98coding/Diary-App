import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.navigation"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(libs.kotlinx.datetime)
                api(libs.androidx.navigation3.runtime)
            }
        }

        getByName("nonAndroidMain") {
            dependencies {
                implementation(libs.kotlinx.serialization.core)
            }
        }
    }
}
