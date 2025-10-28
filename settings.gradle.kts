rootProject.name = "Diary-App"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            content {
                includeGroupByRegex("org.jetbrains.skiko.*")
                includeGroupByRegex("org.jetbrains.compose.*")
                includeGroupByRegex("org.jetbrains.androidx.*")
            }
        }

        mavenLocal()
    }

    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()

        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            content {
                includeGroupByRegex("org.jetbrains.skiko.*")
                includeGroupByRegex("org.jetbrains.compose.*")
                includeGroupByRegex("org.jetbrains.androidx.*")
            }
        }

        mavenLocal()
    }

    versionCatalogs {
        create("ktorLibs") {
            from("io.ktor:ktor-version-catalog:3.3.1")
        }
    }
}

include(":app")

include(":compose:calendar")
include(":compose:core")
include(":compose:memo")

include(":core:coroutines")
include(":core:database")
include(":core:datastore")
include(":core:entity")
include(":core:holiday-database")
include(":core:holiday-preferences")
include(":core:holiday-service")
include(":core:ip-service")
include(":core:ktor-client")
include(":core:location")
include(":core:logger")
include(":core:mapper")
include(":core:navigation")
include(":core:notification")
include(":core:preferences")
include(":core:service")
include(":core:weather-service")
include(":core:work")

include(":data:account")
include(":data:buddy")
include(":data:buddy-group")
include(":data:calendar")
include(":data:credentials")
include(":data:holiday")
include(":data:location")
include(":data:memo")
include(":data:push")
include(":data:sync")
include(":data:tag")
include(":data:weather")

include(":domain:account")
include(":domain:buddy")
include(":domain:buddy-group")
include(":domain:calendar")
include(":domain:credentials")
include(":domain:holiday")
include(":domain:location")
include(":domain:memo")
include(":domain:push")
include(":domain:sync")
include(":domain:tag")
include(":domain:weather")

include(":feature:buddy-group")
include(":feature:calendar")
include(":feature:dday")
include(":feature:login")
include(":feature:memo")
include(":feature:more")
include(":feature:search")
include(":feature:tag")

include(":presenter:calendar")
include(":presenter:memo")
include(":presenter:tag")

include(":testing:ktor")
include(":testing:resources")

include(":library:compose-color")
include(":library:compose-ui")
include(":library:compose-foundation")
include(":library:firebase-core")
include(":library:firebase-messaging")
include(":library:google-credentials")
include(":library:google-credentials-compose")
include(":library:koin-datastore")
include(":library:koin-room")
include(":library:kotlinx-datetime")
include(":library:kotlinx-coroutines-core")
include(":library:kotlinx-file")
include(":library:lifecycle-common")
include(":library:material3-adaptive-navigation")
include(":library:navigation3-compat")
include(":library:paging-common")
include(":library:paging-compose")
include(":library:room-common")
