package io.github.taetae98coding.diary.domain.holiday.usecase

import io.github.taetae98coding.diary.domain.holiday.repository.SpecialDayRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.datetime.YearMonth

class FetchSpecialDayUseCaseTest : BehaviorSpec() {
    init {
        val specialDayRepository = mockk<SpecialDayRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = FetchSpecialDayUseCase(
            specialDayRepository = specialDayRepository,
        )

        Given("해당 월의 기념일을 가져오는 경우") {
            val yearMonth = YearMonth(1998, 1)

            When("유즈케이스를 실행하면") {
                val result = useCase(yearMonth)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) { specialDayRepository.fetch(yearMonth) }
                }
            }
        }
    }
}
