import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.location"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.ipService)
                implementation(projects.domain.location)
            }
        }
    }
}
