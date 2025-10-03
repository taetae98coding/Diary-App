import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.buddy.group"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.core.coroutines)

                implementation(projects.domain.account)
                implementation(projects.domain.buddy)
                implementation(projects.domain.buddyGroup)
                implementation(projects.domain.memo)
                implementation(projects.domain.tag)

                implementation(projects.presenter.calendar)
                implementation(projects.presenter.memo)
                implementation(projects.presenter.tag)

                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
                implementation(projects.library.kotlinxDatetime)
                implementation(projects.library.navigation3Compat)
                implementation(projects.library.pagingCompose)
            }
        }
    }
}
