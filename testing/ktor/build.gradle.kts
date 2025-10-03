import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.testing.ktor"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.testing.resources)
                implementation(ktorLibs.client.mock)
            }
        }
    }
}
