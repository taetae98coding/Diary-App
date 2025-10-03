import com.android.build.api.dsl.androidLibrary

plugins {
    id("diary.primitive.android.library")
    id("diary.primitive.kotest")
}

kotlin {
    androidLibrary {
        namespace = "${Build.NAMESPACE}.core.mapper"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.database)
                implementation(projects.core.entity)
                implementation(projects.core.holidayDatabase)
                implementation(projects.core.holidayService)
                implementation(projects.core.preferences)
                implementation(projects.core.service)
                implementation(projects.core.weatherService)
            }
        }
    }
}
