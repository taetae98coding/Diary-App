import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.search"
    }

    sourceSets {
        commonMain {
            dependencies {
            }
        }
    }
}
