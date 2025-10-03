package io.github.taetae98coding.diary.core.weather.service.datasource

import io.github.taetae98coding.diary.core.ktor.client.di.WeatherClient
import io.github.taetae98coding.diary.core.weather.service.entity.ForecastRemoteEntity
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherRemoteEntity
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
    public suspend fun getWeather(latitude: Double, longitude: Double): WeatherRemoteEntity {
        val response = client.get("/data/2.5/weather") {
            parameter("lat", latitude)
            parameter("lon", longitude)
        }

        return response.body()
    }

    public suspend fun getForecast(latitude: Double, longitude: Double): ForecastRemoteEntity {
        val response = client.get("/data/2.5/forecast") {
            parameter("lat", latitude)
            parameter("lon", longitude)
        }

        return response.body()
    }
}
