import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.memo"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.memo)
                implementation(projects.domain.tag)
                implementation(projects.presenter.memo)
                implementation(projects.library.pagingCompose)

                implementation(libs.androidx.paging.compose)
            }
        }
    }
}
