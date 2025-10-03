plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.compose.calendar"
    }

    sourceSets {
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
