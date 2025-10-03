import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.holiday.preferences"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.datastore)
                implementation(libs.kotlinx.datetime)
                implementation(libs.androidx.datastore.preferences)
            }
        }
    }
}
