import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.weather"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.weatherService)
                implementation(projects.domain.weather)
            }
        }
    }
}
