package io.github.taetae98coding.diary.initializer

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.taetae98coding.diary.BuildKonfig
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.core.logger.napier.NapierLogSender
import io.github.taetae98coding.diary.di.AppModule
import io.github.taetae98coding.diary.di.JvmAppModule
import io.github.taetae98coding.diary.di.NonAndroidAppModule
import io.github.taetae98coding.diary.library.google.credentials.GoogleCredentialsSdk
import io.github.taetae98coding.diary.library.koin.datastore.KoinComponentDataStore
import io.github.taetae98coding.diary.library.koin.room.KoinComponentRoom
import io.github.taetae98coding.diary.library.kotlinx.file.homePath
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

internal data object Initializer {

    suspend fun init() {
        withContext<Unit>(Dispatchers.Default) {
            initKoin()
            coroutineScope {
                launch { initKoinComponent() }
                launch { initGoogleCredentials() }
                launch { initLogger() }
            }
        }
    }

    private fun initKoin(): KoinApplication {
        return startKoin {
            modules(
                AppModule().module,
                JvmAppModule().module,
                NonAndroidAppModule().module,
            )
        }
    }

    private fun initKoinComponent() {
        KoinComponentDataStore.storePath(File(homePath(), BuildKonfig.STORE_PATH).absolutePath)
        KoinComponentRoom.storePath(File(homePath(), BuildKonfig.STORE_PATH).absolutePath)
    }

    private fun initGoogleCredentials(): GoogleCredentialsSdk {
        return GoogleCredentialsSdk.redirectUri(BuildKonfig.GOOGLE_REDIRECT_URI)
            .clientId(BuildKonfig.GOOGLE_CLIENT_ID)
            .clientSecret(BuildKonfig.GOOGLE_CLIENT_SECRET)
    }

    private fun initLogger(): Logger {
        Napier.base(DebugAntilog())

        return Logger.add(Any::class, NapierLogSender())
    }
}
