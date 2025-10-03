plugins {
    id("diary.primitive.android.library")
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.kotlin.cocoapods)
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.library.google.credentials"
    }

    cocoapods {
        ios.deploymentTarget = "18.0"

        noPodspec()
        pod(name = "GoogleSignIn", version = "9.0.0")
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.android.googleid)
                implementation(libs.androidx.credentials)
            }
        }

        jvmMain {
            dependencies {
                implementation(ktorLibs.client.core)
                implementation(ktorLibs.client.contentNegotiation)
                implementation(ktorLibs.server.cio)
                implementation(ktorLibs.serialization.kotlinx.json)
            }
        }

        iosMain {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
