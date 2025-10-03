package io.github.taetae98coding.diary.di

import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.github.taetae98coding.diary.core.location.LocationIosModule
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(
    includes = [
        LocationIosModule::class,
    ],
)
internal class IosAppModule {
    @Factory
    @ApiEngine
    fun providesApiEngin(): HttpClientEngine {
        return Darwin.create()
    }
}
