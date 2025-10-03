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
