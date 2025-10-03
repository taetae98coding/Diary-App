package io.github.taetae98coding.diary.domain.holiday.usecase

import io.github.taetae98coding.diary.domain.holiday.repository.HolidayRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.datetime.YearMonth

class FetchHolidayUseCaseTest : BehaviorSpec() {
    init {
        val holidayRepository = mockk<HolidayRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = FetchHolidayUseCase(
            holidayRepository = holidayRepository,
        )

        Given("해당 월의 공휴일을 가져오는 경우") {
            val yearMonth = YearMonth(1998, 1)

            When("유즈케이스를 실행하면") {
                val result = useCase(yearMonth)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) { holidayRepository.fetch(yearMonth) }
                }
            }
        }
    }
}
