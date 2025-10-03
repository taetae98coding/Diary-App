import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.domain")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.domain.weather"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.location)
            }
        }
    }
}
