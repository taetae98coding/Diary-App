plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.compose.memo"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.compose.core)
                implementation(projects.core.entity)
            }
        }
    }
}
