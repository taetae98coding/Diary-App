import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.domain")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.domain.location"
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.library.kotlinxLocation)
            }
        }
    }
}
