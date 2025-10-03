plugins {
    id("diary.primitive.android.library")
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.firebase.messaging"
    }

    cocoapods {
        ios.deploymentTarget = "18.0"

        noPodspec()
        pod(name = "FirebaseMessaging", version = "12.3.0")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.library.firebaseCore)
            }
        }

        androidMain {
            dependencies {
                implementation(project.dependencies.platform(libs.android.firebase.bom))
                implementation(libs.android.firebase.messaging)
            }
        }
    }
}
