plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.compose")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.presenter.tag"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.compose.core)
                implementation(projects.core.logger)
                implementation(projects.domain.tag)
                implementation(projects.library.composeColor)
                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
                implementation(projects.library.pagingCompose)

                implementation(libs.lifecycle.runtime.compose)
            }
        }
    }
}
