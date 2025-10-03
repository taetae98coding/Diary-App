import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.sync"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.database)
                implementation(projects.core.preferences)
                implementation(projects.core.service)
                implementation(projects.core.work)
                implementation(projects.domain.sync)
            }
        }
    }
}
