import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.push"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.service)
                implementation(projects.domain.push)
                implementation(projects.library.firebaseMessaging)
            }
        }
    }
}
