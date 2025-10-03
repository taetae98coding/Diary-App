package io.github.taetae98coding.diary.initializer

import android.content.Context
import androidx.startup.Initializer
import io.github.taetae98coding.diary.di.AndroidAppModule
import io.github.taetae98coding.diary.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

internal class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            workManagerFactory()

            modules(
                AppModule().module,
                AndroidAppModule().module,
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
