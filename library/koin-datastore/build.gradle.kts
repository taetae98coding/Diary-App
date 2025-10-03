plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.koin.datastore"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.kotlinxFile)
                implementation(libs.androidx.datastore.preferences)
            }
        }
    }
}
