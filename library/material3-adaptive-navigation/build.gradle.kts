plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.material3.adaptive.navigation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.compose.ui.backhandler)
                api(libs.compose.material3.adaptive.navigation)
            }
        }
    }
}
