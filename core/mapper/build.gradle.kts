import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.kotest")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.mapper"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.core.entity)
                implementation(projects.core.database)
                implementation(projects.core.preferences)
                implementation(projects.core.service)
                implementation(projects.core.holidayDatabase)
                implementation(projects.core.holidayService)
                implementation(projects.core.ipService)
                implementation(projects.core.weatherService)
            }
        }
    }
}
