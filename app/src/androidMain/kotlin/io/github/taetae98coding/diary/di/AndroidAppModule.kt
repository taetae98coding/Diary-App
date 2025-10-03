package io.github.taetae98coding.diary.di

import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.github.taetae98coding.diary.core.location.LocationAndroidModule
import io.github.taetae98coding.diary.core.notification.NotificationAndroidModule
import io.github.taetae98coding.diary.core.work.WorkAndroidModule
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(
    includes = [
        WorkAndroidModule::class,
        LocationAndroidModule::class,
        NotificationAndroidModule::class,
    ],
)
internal class AndroidAppModule {
    @Factory
    @ApiEngine
    fun providesApiEngin(): HttpClientEngine {
        return OkHttp.create()
    }
}
