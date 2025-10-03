plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.presenter.tag"
    }

    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.compose.core)
                implementation(projects.core.coroutines)
                implementation(projects.core.logger)
                implementation(projects.domain.tag)
                implementation(projects.library.composeColor)
                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
                implementation(projects.library.pagingCompose)

                implementation(libs.lifecycle.runtime.compose)
            }
        }
    }
}
