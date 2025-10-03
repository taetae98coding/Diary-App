import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.data")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.data.holiday"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.core.holidayDatabase)
                implementation(projects.core.holidayPreferences)
                implementation(projects.core.holidayService)
                implementation(projects.domain.holiday)
                implementation(projects.library.kotlinxDatetime)
            }
        }
    }
}
