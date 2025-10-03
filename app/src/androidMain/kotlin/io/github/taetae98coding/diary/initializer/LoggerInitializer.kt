package io.github.taetae98coding.diary.initializer

import android.content.Context
import androidx.startup.Initializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.taetae98coding.diary.BuildConfig
import io.github.taetae98coding.diary.core.logger.Logger
import io.github.taetae98coding.diary.core.logger.napier.NapierLogSender

internal class LoggerInitializer : Initializer<Logger> {
    override fun create(context: Context): Logger {
        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
        }

        return Logger.add(Any::class, NapierLogSender())
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?> {
        return emptyList()
    }
}
