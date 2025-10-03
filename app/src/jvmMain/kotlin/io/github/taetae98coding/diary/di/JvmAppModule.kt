package io.github.taetae98coding.diary.di

import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
internal class JvmAppModule {
    @Factory
    @ApiEngine
    fun providesApiEngin(): HttpClientEngine {
        return OkHttp.create()
    }
}
