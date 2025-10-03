plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.room.common"
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.uuid.ExperimentalUuidApi")
        }

        commonMain {
            dependencies {
                implementation(libs.kotlinx.datetime)
                api(libs.androidx.room.common)
            }
        }
    }
}
