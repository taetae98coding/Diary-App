package io.github.taetae98coding.diary.domain.location.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.domain.location.repository.LocationRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetLocationUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val locationRepository = mockk<LocationRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetLocationUseCase(
            locationRepository = locationRepository,
        )

        Given("위치가 존재하는 경우") {
            val location = fixture.giveMeOne<Location>()
            every { locationRepository.get() } returns flowOf(location)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("Repository 결과를 그대로 방출한다") {
                    result.shouldBeSuccess(location)
                }
                Then("Repository를 호출한다") {
                    verify(exactly = 1) { locationRepository.get() }
                }
            }
        }
    }
}
