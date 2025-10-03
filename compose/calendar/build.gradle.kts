plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.compose.calendar"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.compose.core)
                implementation(projects.library.composeColor)
                implementation(projects.library.kotlinxDatetime)

                implementation(libs.coil.compose)
            }
        }
    }
}
