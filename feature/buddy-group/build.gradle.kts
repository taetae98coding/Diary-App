import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.convention.feature")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.feature.buddy.group"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.domain.account)
                implementation(projects.domain.buddy)
                implementation(projects.domain.buddyGroup)
                implementation(projects.domain.memo)
                implementation(projects.domain.tag)

                implementation(projects.presenter.calendar)
                implementation(projects.presenter.memo)
                implementation(projects.presenter.tag)

                implementation(projects.library.composeUi)
                implementation(projects.library.composeFoundation)
                implementation(projects.library.kotlinxDatetime)
                implementation(projects.library.navigation3Compat)
                implementation(projects.library.pagingCompose)
            }
        }
    }
}
