import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.more"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.coroutines)

                implementation(projects.compose.calendar)

                implementation(projects.domain.account)
                implementation(projects.domain.credentials)
                implementation(projects.domain.holiday)

                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
            }
        }
    }
}
