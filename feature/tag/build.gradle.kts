import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.tag"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.memo)
                implementation(projects.domain.tag)
                implementation(projects.presenter.memo)
                implementation(projects.presenter.tag)
            }
        }
    }
}
