import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.domain")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.domain.location"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }
    }
}
