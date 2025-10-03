import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.calendar"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.database)
                implementation(projects.domain.calendar)
            }
        }
    }
}
