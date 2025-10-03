package io.github.taetae98coding.diary.core.ktor.client

import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.github.taetae98coding.diary.core.ktor.client.di.ApiJson
import io.github.taetae98coding.diary.core.ktor.client.di.DiaryApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.DiaryClient
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayApiKey
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayClient
import io.github.taetae98coding.diary.core.ktor.client.di.IpClient
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherAppId
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(
    includes = [
        ApiJsonModule::class,
    ],
)
@ComponentScan
public class KtorClientModule {

    @Single
    @DiaryClient
    internal fun providesDiaryClient(
        @ApiEngine
        engine: HttpClientEngine,
        @ApiJson
        json: Json,
        @DiaryApiUrl
        apiUrl: String,
    ): HttpClient {
        return HttpClient(engine) {
            install(DefaultRequest) {
                url.takeFrom(apiUrl)
            }

            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Single
    @WeatherClient
    internal fun providesWeatherClient(
        @ApiEngine
        engine: HttpClientEngine,
        @ApiJson
        json: Json,
        @WeatherApiUrl
        apiUrl: String,
        @WeatherAppId
        appId: String,
    ): HttpClient {
        return HttpClient(engine) {
            install(DefaultRequest) {
                url.takeFrom(apiUrl)
                url.parameters.append("appid", appId)
                url.parameters.append("units", "metric")
                url.parameters.append("lang", "kr")
            }

            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Single
    @IpClient
    internal fun providesIpClient(
        @ApiEngine
        engine: HttpClientEngine,
        @ApiJson
        json: Json,
    ): HttpClient {
        return HttpClient(engine) {
            install(DefaultRequest) {
                url.takeFrom("http://ip-api.com/")
            }

            install(ContentNegotiation) {
                json(json)
            }
        }
    }

    @Single
    @HolidayClient
    internal fun providesHolidayClient(
        @ApiEngine
        engine: HttpClientEngine,
        @ApiJson
        json: Json,
        @HolidayApiUrl
        apiUrl: String,
        @HolidayApiKey
        apiKey: String,
    ): HttpClient {
        return HttpClient(engine) {
            install(DefaultRequest) {
                url.takeFrom(apiUrl)
                url.parameters.append("serviceKey", apiKey)
            }

            install(ContentNegotiation) {
                json(json)
            }
        }
    }
}
