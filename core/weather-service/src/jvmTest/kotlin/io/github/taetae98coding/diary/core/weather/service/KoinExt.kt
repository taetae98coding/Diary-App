package io.github.taetae98coding.diary.core.weather.service

import io.github.taetae98coding.diary.core.ktor.client.KtorClientModule
import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherAppId
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module

fun fakeWeatherClientModule(
    engine: HttpClientEngine,
): Module {
    return module {
        includes(
            WeatherServiceModule().module,
            KtorClientModule().module,
            module {
                single(qualifier = StringQualifier(ApiEngine::class.qualifiedName.orEmpty())) { engine }
                single(qualifier = StringQualifier(WeatherAppId::class.qualifiedName.orEmpty())) { "" }
                single(qualifier = StringQualifier(WeatherApiUrl::class.qualifiedName.orEmpty())) { "" }
            },
        )
    }
}
