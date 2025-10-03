package io.github.taetae98coding.diary.domain.holiday.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import io.github.taetae98coding.diary.core.entity.holiday.Holiday
import io.github.taetae98coding.diary.domain.holiday.repository.HolidayRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.YearMonth

class GetHolidayUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val holidayRepository = mockk<HolidayRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetHolidayUseCase(
            holidayRepository = holidayRepository,
        )

        Given("해당 월의 공휴일이 존재하는 경우") {
            val yearMonth = YearMonth(1998, 1)
            val holidays = fixture.giveMe<Holiday>(10)

            every { holidayRepository.get(yearMonth) } returns flowOf(holidays)

            When("유즈케이스를 구독하면") {
                val result = useCase(yearMonth).first()

                Then("Repository 결과를 그대로 방출한다") {
                    result.shouldBeSuccess(holidays)
                }
                Then("Repository를 호출한다") {
                    verify(exactly = 1) { holidayRepository.get(yearMonth) }
                }
            }
        }
    }
}
