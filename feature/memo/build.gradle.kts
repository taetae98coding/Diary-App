import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.memo"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.domain.memo)
                implementation(projects.domain.tag)
                implementation(projects.presenter.memo)
                implementation(projects.library.pagingCompose)
                implementation(projects.library.navigation3Compat)

                implementation(libs.androidx.paging.compose)
            }
        }
    }
}
