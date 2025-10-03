plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.compose.core"

        androidResources {
            enable = true
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.library.composeColor)
                implementation(projects.library.kotlinxDatetime)
                implementation(projects.library.pagingCompose)

                implementation(compose.animation)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)
                implementation(compose.preview)

                implementation(libs.coil.compose)
                implementation(libs.markdown.compose)
                implementation(libs.lifecycle.runtime.compose)

                api(compose.material3)
            }
        }
    }
}

compose {
    resources {
        publicResClass = false
        packageOfResClass = "io.github.taetae98coding.diary.compose.core"
        generateResClass = auto
    }
}
