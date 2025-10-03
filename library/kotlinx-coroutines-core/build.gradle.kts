plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.kotlinx.coroutines.core"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.coroutines.core)
            }
        }
    }
}
