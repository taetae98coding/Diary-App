plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.paging.common"
    }

    sourceSets {
        all {
            languageSettings.optIn("androidx.paging.ExperimentalPagingApi")
        }

        commonMain {
            dependencies {
                api(libs.androidx.paging.common)
            }
        }
    }
}
