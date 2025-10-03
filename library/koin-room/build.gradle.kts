plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.koin.room"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.kotlinxFile)
                implementation(libs.androidx.room.runtime)
            }
        }
    }
}
