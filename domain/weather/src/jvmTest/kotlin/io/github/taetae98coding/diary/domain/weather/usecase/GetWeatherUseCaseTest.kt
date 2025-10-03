package io.github.taetae98coding.diary.domain.weather.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.core.entity.weather.Weather
import io.github.taetae98coding.diary.domain.location.usecase.GetLocationUseCase
import io.github.taetae98coding.diary.domain.weather.repository.WeatherRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.time.Instant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetWeatherUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getLocationUseCase = mockk<GetLocationUseCase>(relaxed = true, relaxUnitFun = true)
        val weatherRepository = mockk<WeatherRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetWeatherUseCase(
            getLocationUseCase = getLocationUseCase,
            weatherRepository = weatherRepository,
        )

        Given("위치가 조회되고 날씨가 존재하는 경우") {
            val location = fixture.giveMeOne<Location>()
            every { getLocationUseCase() } returns flowOf(Result.success(location))

            val weathers = List(5) {
                fixture.giveMeKotlinBuilder<Weather>()
                    .set(Weather::instant, Instant.DISTANT_PAST)
                    .sample()
            }
            every { weatherRepository.get(location) } returns flowOf(weathers)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("Repository 결과를 그대로 방출한다") {
                    result.shouldBeSuccess(weathers)
                }
                Then("Repository 호출한다") {
                    verify(exactly = 1) { weatherRepository.get(location) }
                }
            }
        }
    }
}
