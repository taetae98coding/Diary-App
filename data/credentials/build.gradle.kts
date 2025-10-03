import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.credentials"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.preferences)
                implementation(projects.core.service)
                implementation(projects.domain.credentials)
            }
        }
    }
}
