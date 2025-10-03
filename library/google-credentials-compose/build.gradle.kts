plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.google.credentials.compose"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(compose.runtime)
                api(projects.library.googleCredentials)
            }
        }

        androidMain {
            dependencies {
                implementation(compose.ui)
            }
        }

        iosMain {
            dependencies {
                implementation(compose.ui)
            }
        }
    }
}
