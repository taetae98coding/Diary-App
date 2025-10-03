import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.datastore"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.koinDatastore)
                implementation(libs.androidx.datastore.preferences)
            }
        }
    }
}
