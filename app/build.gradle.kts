import com.codingfeline.buildkonfig.compiler.FieldSpec
import io.github.taetae98coding.diary.gradle.getLocalProperties
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family

private val localProperties = getLocalProperties()

plugins {
    alias(libs.plugins.android.application)
    id("diary.primitive.android.application")
    id("diary.primitive.compose")
    id("diary.primitive.koin.annotations")
    id("diary.primitive.koin.compiler")
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.compose.hot.reload)
    alias(libs.plugins.dependency.guard)
}

kotlin {
    targets.withType<KotlinNativeTarget>()
        .filter { it.konanTarget.family == Family.IOS }
        .forEach { target ->
            target.binaries {
                framework {
                    baseName = "DiaryKotlin"
                    isStatic = true

                    binaryOption("bundleId", "io.github.taetae98coding.diary.kotlin")
                }
            }
        }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        commonMain {
            dependencies {
                implementation(projects.compose.core)

                implementation(projects.core.coroutines)
                implementation(projects.core.database)
                implementation(projects.core.datastore)
                implementation(projects.core.holidayDatabase)
                implementation(projects.core.holidayPreferences)
                implementation(projects.core.holidayService)
                implementation(projects.core.ipService)
                implementation(projects.core.ktorClient)
                implementation(projects.core.location)
                implementation(projects.core.logger)
                implementation(projects.core.preferences)
                implementation(projects.core.navigation)
                implementation(projects.core.notification)
                implementation(projects.core.service)
                implementation(projects.core.weatherService)
                implementation(projects.core.work)

                implementation(projects.data.account)
                implementation(projects.data.buddy)
                implementation(projects.data.buddyGroup)
                implementation(projects.data.calendar)
                implementation(projects.data.credentials)
                implementation(projects.data.holiday)
                implementation(projects.data.location)
                implementation(projects.data.memo)
                implementation(projects.data.push)
                implementation(projects.data.sync)
                implementation(projects.data.tag)
                implementation(projects.data.weather)

                implementation(projects.domain.account)
                implementation(projects.domain.buddy)
                implementation(projects.domain.buddyGroup)
                implementation(projects.domain.calendar)
                implementation(projects.domain.credentials)
                implementation(projects.domain.holiday)
                implementation(projects.domain.location)
                implementation(projects.domain.memo)
                implementation(projects.domain.push)
                implementation(projects.domain.sync)
                implementation(projects.domain.tag)
                implementation(projects.domain.weather)

                implementation(projects.feature.buddyGroup)
                implementation(projects.feature.calendar)
                implementation(projects.feature.login)
                implementation(projects.feature.memo)
                implementation(projects.feature.more)
                implementation(projects.feature.search)
                implementation(projects.feature.tag)

                implementation(projects.presenter.calendar)
                implementation(projects.presenter.memo)
                implementation(projects.presenter.tag)

                implementation(projects.library.composeUi)
                implementation(projects.library.navigation3Compat)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.compose.viewmodel.navigation)

                implementation(libs.napier)

                implementation(compose.material3AdaptiveNavigationSuite)
                implementation(libs.lifecycle.runtime.compose)
                implementation(libs.lifecycle.viewmodel.navigation3)
                implementation(libs.navigation3.ui)
                implementation(libs.androidx.navigation3.runtime)

                implementation(libs.coil.compose)
                implementation(libs.coil.ktor)
            }
        }

        androidMain {
            dependencies {
                implementation(projects.library.googleCredentials)

                implementation(libs.koin.android)
                implementation(libs.koin.androidx.workmanager)

                implementation(project.dependencies.platform(libs.android.firebase.bom))
                implementation(libs.android.firebase.analytics)
                implementation(libs.android.firebase.crashlytics.ndk)
                implementation(libs.android.firebase.messaging)
                implementation(libs.android.firebase.perf)

                implementation(libs.android.material)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.splash)
                implementation(libs.androidx.startup.runtime)

                implementation(ktorLibs.client.okhttp)
            }
        }

        jvmMain {
            dependencies {
                implementation(projects.library.googleCredentials)
                implementation(projects.library.koinDatastore)
                implementation(projects.library.koinRoom)
                implementation(projects.library.kotlinxFile)

                implementation(ktorLibs.client.okhttp)

                runtimeOnly(libs.kotlinx.coroutines.swing)
                runtimeOnly(compose.desktop.currentOs)
            }
        }

        iosMain {
            dependencies {
                implementation(ktorLibs.client.darwin)
            }
        }
    }

//    swiftExport {
//        moduleName = "DiaryKotlin"
//        flattenPackage = Build.NAMESPACE
//    }
}

android {
    namespace = Build.NAMESPACE

    defaultConfig {
        applicationId = "io.github.taetae98coding.diary"

        versionCode = 1
        versionName = Build.VERSION_NAME
    }

    signingConfigs {
        create("dev") {
            storeFile = file("keystore/dev.jks")
            storePassword = localProperties.getProperty("android.dev.key.store.password")
            keyAlias = localProperties.getProperty("android.dev.key.alias")
            keyPassword = localProperties.getProperty("android.dev.key.password")
        }

        create("real") {
            storeFile = file("keystore/real.jks")
            storePassword = localProperties.getProperty("android.real.key.store.password")
            keyAlias = localProperties.getProperty("android.real.key.alias")
            keyPassword = localProperties.getProperty("android.real.key.password")
        }
    }

    flavorDimensions += listOf("product")
    productFlavors {
        create("dev") {
            dimension = "product"
            applicationIdSuffix = ".dev"
            signingConfig = signingConfigs.getByName("dev")
        }
        create("real") {
            dimension = "product"
            applicationIdSuffix = null
            signingConfig = signingConfigs.getByName("real")
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            signingConfig = null

            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            applicationIdSuffix = null
            signingConfig = null

            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

compose {
    desktop {
        application {
            mainClass = "io.github.taetae98coding.diary.JvmAppKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg)

                packageName = "Diary"
                version = Build.VERSION_NAME

                // TODO remove
                // :app:suggestRuntimeModules
                includeAllModules = true

                macOS {
                    appStore = true
                    bundleID = "io.github.taetae98coding.diary"

                    iconFile.set(rootProject.file("assets/icon/app_icon_mac.icns"))
                    infoPlist {
                        extraKeysRawXml = """
                        <key>NSAppTransportSecurity</key>
                        <dict>
                            <key>NSAllowsArbitraryLoads</key>
                            <true/>
                        </dict>
                        """.trimIndent()
                    }
                }
            }

            buildTypes {
                release {
                    proguard {
                        obfuscate.set(true)
                        joinOutputJars.set(true)
                    }
                }
            }
        }
    }
}

dependencies {
    debugImplementation(libs.leakcanary.android)
}

buildkonfig {
    packageName = Build.NAMESPACE

    defaultConfigs {
    }

    defaultConfigs("dev") {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "DIARY_API_URL",
            value = localProperties.getProperty("diary.dev.api.url"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "WEATHER_API_URL",
            value = localProperties.getProperty("weather.dev.api.url"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "WEATHER_API_APP_ID",
            value = localProperties.getProperty("weather.dev.api.app.id"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "HOLIDAY_API_URL",
            value = localProperties.getProperty("holiday.dev.api.url"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "HOLIDAY_API_KEY",
            value = localProperties.getProperty("holiday.dev.api.key"),
            nullable = false,
            const = true,
        )
    }

    defaultConfigs("real") {
        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "DIARY_API_URL",
            value = localProperties.getProperty("diary.real.api.url"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "WEATHER_API_URL",
            value = localProperties.getProperty("weather.real.api.url"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "WEATHER_API_APP_ID",
            value = localProperties.getProperty("weather.real.api.app.id"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "HOLIDAY_API_URL",
            value = localProperties.getProperty("holiday.real.api.url"),
            nullable = false,
            const = true,
        )

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "HOLIDAY_API_KEY",
            value = localProperties.getProperty("holiday.real.api.key"),
            nullable = false,
            const = true,
        )
    }

    targetConfigs("dev") {
        create("android") {
            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_CLIENT_ID",
                value = localProperties.getProperty("google.dev.client.id"),
                nullable = false,
                const = true,
            )
        }

        create("jvm") {
            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_REDIRECT_URI",
                value = localProperties.getProperty("google.dev.redirect.uri"),
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_CLIENT_ID",
                value = localProperties.getProperty("google.dev.client.id"),
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_CLIENT_SECRET",
                value = localProperties.getProperty("google.dev.client.secret"),
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "APP_NAME",
                value = "Diary Dev",
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "STORE_PATH",
                value = "diary-dev",
                nullable = false,
                const = true,
            )
        }
    }

    targetConfigs("real") {
        create("android") {
            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_CLIENT_ID",
                value = localProperties.getProperty("google.real.client.id"),
                nullable = false,
                const = true,
            )
        }

        create("jvm") {
            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_REDIRECT_URI",
                value = localProperties.getProperty("google.real.redirect.uri"),
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_CLIENT_ID",
                value = localProperties.getProperty("google.real.client.id"),
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "GOOGLE_CLIENT_SECRET",
                value = localProperties.getProperty("google.real.client.secret"),
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "APP_NAME",
                value = "Diary",
                nullable = false,
                const = true,
            )

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = "STORE_PATH",
                value = "diary-real",
                nullable = false,
                const = true,
            )
        }
    }
}

dependencyGuard {
    configuration("realReleaseRuntimeClasspath")
    configuration("jvmMainRuntimeClasspath") {
        baselineMap = { dependencyName ->
            dependencyName.replace("linux-x64", "")
                .replace("macos-arm64", "")
        }
    }
}
