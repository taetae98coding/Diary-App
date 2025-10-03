import com.android.build.api.dsl.androidLibrary
import io.github.taetae98coding.diary.gradle.kspAll

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.koin.core")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
    alias(libs.plugins.androidx.room)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.holiday.database"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.library.koinRoom)
                implementation(projects.library.roomCommon)

                implementation(libs.kotlinx.datetime)
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)
            }
        }
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    kspAll(libs.androidx.room.compiler)
}
