plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.material3.adaptive.navigation"
    }

    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi")
            languageSettings.optIn("androidx.compose.animation.ExperimentalSharedTransitionApi")
            languageSettings.optIn("androidx.compose.ui.ExperimentalComposeUiApi")
        }

        commonMain {
            dependencies {
                implementation(libs.compose.ui.backhandler)
                api(libs.compose.material3.adaptive.navigation)
            }
        }
    }
}
