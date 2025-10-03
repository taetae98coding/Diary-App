import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.buddy.group"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.database)
                implementation(projects.core.service)
                implementation(projects.domain.buddyGroup)
                implementation(projects.domain.memo)
            }
        }
    }
}
