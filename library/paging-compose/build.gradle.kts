plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.paging.compose"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.library.pagingCommon)
                api(libs.androidx.paging.compose)
            }
        }
    }
}
