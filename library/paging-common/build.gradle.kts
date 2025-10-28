plugins {
    id("diary.primitive.android.library")
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
