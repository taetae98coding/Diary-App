package io.github.taetae98coding.diary.core.weather.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.WeatherClient
import io.github.taetae98coding.diary.core.weather.service.entity.ForecastRemoteEntity
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherRemoteEntity
import io.github.taetae98coding.diary.library.kotlinx.location.Location
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Factory

@Factory
public class WeatherRemoteDataSource internal constructor(
    @param:WeatherClient
    private val client: HttpClient,
) {
    public suspend fun getWeather(location: Location): WeatherRemoteEntity {
        val response = client.get("/data/2.5/weather") {
            parameter("lat", location.latitude)
            parameter("lon", location.longitude)
        }

        return response.body()
    }

    public suspend fun getForecast(location: Location): ForecastRemoteEntity {
        val response = client.get("/data/2.5/forecast") {
            parameter("lat", location.latitude)
            parameter("lon", location.longitude)
        }

        return response.body()
    }
}
