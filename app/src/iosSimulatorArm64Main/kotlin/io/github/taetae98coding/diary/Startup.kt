package io.github.taetae98coding.diary

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.taetae98coding.diary.di.AppModule
import io.github.taetae98coding.diary.di.IosAppModule
import io.github.taetae98coding.diary.di.NonAndroidAppModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

@Suppress("FunctionName")
public fun Koin() {
    startKoin {
        modules(
            AppModule().module,
            IosAppModule().module,
            NonAndroidAppModule().module,
        )
    }
}

@Suppress("FunctionName")
public fun Napier() {
    Napier.base(DebugAntilog())
}
