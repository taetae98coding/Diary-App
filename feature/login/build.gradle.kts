import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.login"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.account)
                implementation(projects.domain.credentials)
                implementation(projects.library.googleCredentialsCompose)
            }
        }
    }
}
