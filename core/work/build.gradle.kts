import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.work"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.database)
                implementation(projects.core.entity)
                implementation(projects.core.mapper)
                implementation(projects.core.preferences)
                implementation(projects.core.service)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.work.runtime)
                implementation(libs.koin.androidx.workmanager)
            }
        }

        nonAndroidMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
