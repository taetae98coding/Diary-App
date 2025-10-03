package io.github.taetae98coding.diary.core.weather.service

import io.github.taetae98coding.diary.core.weather.service.datasource.WeatherRemoteDataSource
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherMainRemoteEntity
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherRemoteEntity
import io.github.taetae98coding.diary.core.weather.service.entity.WeatherTypeRemoteEntity
import io.github.taetae98coding.diary.testing.ktor.mockEngine
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Instant
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class WeatherRemoteDataSourceTest : FunSpec() {
    init {
        test("weather.json") {
            val engine = mockEngine("/weather.json")
            val application = startKoin {
                modules(fakeWeatherClientModule(engine))
            }
            val dataSource = application.koin.get<WeatherRemoteDataSource>()
            val response = dataSource.getWeather(0.0, 0.0)

            response shouldBe WeatherRemoteEntity(
                type = listOf(
                    WeatherTypeRemoteEntity(
                        id = 800,
                        description = "맑음",
                        iconId = "01n",
                    ),
                ),
                main = WeatherMainRemoteEntity(
                    temperature = 29.99,
                ),
                instant = Instant.fromEpochSeconds(1753788423),
            )

            stopKoin()
        }

        test("forecast.json") {
            val engine = mockEngine("/forecast.json")
            val application = startKoin {
                modules(fakeWeatherClientModule(engine))
            }
            val dataSource = application.koin.get<WeatherRemoteDataSource>()
            val response = dataSource.getForecast(0.0, 0.0)

            response.weatherList.first() shouldBe WeatherRemoteEntity(
                type = listOf(
                    WeatherTypeRemoteEntity(
                        id = 800,
                        description = "맑음",
                        iconId = "01n",
                    ),
                ),
                main = WeatherMainRemoteEntity(
                    temperature = 30.03,
                ),
                instant = Instant.fromEpochSeconds(1753790400),
            )

            stopKoin()
        }
    }
}
