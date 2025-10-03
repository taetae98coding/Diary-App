import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.domain")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.domain.holiday"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
            }
        }
    }
}
