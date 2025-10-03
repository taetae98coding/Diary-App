plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.compose.color"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.ui)
            }
        }
    }
}
