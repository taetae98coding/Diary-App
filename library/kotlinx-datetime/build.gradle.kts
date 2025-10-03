plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.kotlinx.datetime"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
            }
        }
    }
}
