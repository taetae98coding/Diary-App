plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
    id("diary.primitive.koin.core")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.presenter.calendar"
    }

    sourceSets {
        all {
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.compose.core)
                implementation(projects.core.coroutines)
                implementation(projects.core.logger)

                implementation(projects.domain.holiday)
                implementation(projects.domain.weather)

                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
                implementation(projects.library.kotlinxDatetime)
                implementation(projects.library.kotlinxCoroutinesCore)
                implementation(projects.library.pagingCompose)

                implementation(libs.lifecycle.runtime.compose)

                api(projects.compose.calendar)
            }
        }
    }
}
