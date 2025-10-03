plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
    alias(libs.plugins.kotlin.plugin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.presenter.memo"
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
                implementation(projects.compose.memo)
                implementation(projects.core.coroutines)
                implementation(projects.core.logger)
                implementation(projects.core.navigation)
                implementation(projects.domain.memo)
                implementation(projects.library.composeColor)
                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
                implementation(projects.library.navigation3Compat)
                implementation(projects.library.material3AdaptiveNavigation)
                implementation(projects.library.pagingCompose)

                implementation(libs.lifecycle.runtime.compose)
                implementation(libs.navigation3.ui)
            }
        }
    }
}
