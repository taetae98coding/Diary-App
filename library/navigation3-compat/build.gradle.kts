plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.navigation3.compoat"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(libs.navigation3.ui)
                implementation(libs.androidx.navigation3.runtime)
            }
        }
    }
}
