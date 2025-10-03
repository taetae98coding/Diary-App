plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.kotest")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.paging.common"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.androidx.paging.common)
            }
        }
    }
}
