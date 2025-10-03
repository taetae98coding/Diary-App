import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.calendar"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.calendar)
                implementation(projects.presenter.calendar)
                implementation(projects.library.pagingCompose)
                implementation(projects.library.navigation3Compat)

                implementation(libs.androidx.paging.compose)
            }
        }
    }
}
