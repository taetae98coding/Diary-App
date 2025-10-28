import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.dday"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
        }

        commonMain {
            dependencies {
                implementation(projects.core.navigation)
                implementation(projects.library.navigation3Compat)
            }
        }
    }
}
