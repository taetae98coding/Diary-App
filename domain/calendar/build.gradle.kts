import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.domain")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.domain.calendar"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.account)
            }
        }
    }
}
