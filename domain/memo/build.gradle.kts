import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.domain")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.domain.memo"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.account)
                implementation(projects.domain.sync)
            }
        }
    }
}
