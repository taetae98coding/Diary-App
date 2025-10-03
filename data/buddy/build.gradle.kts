import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.buddy"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.service)
                implementation(projects.domain.buddy)
            }
        }
    }
}
