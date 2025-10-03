package io.github.taetae98coding.diary.domain.calendar.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.calendar.repository.CalendarMemoRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange

class GetCalendarMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val calendarMemoRepository = mockk<CalendarMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetCalendarMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            calendarMemoRepository = calendarMemoRepository,
        )

        Given("사용자의 기간 메모를 조회하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val dateRange = LocalDateRange(LocalDate(1998, 1, 9), LocalDate(1998, 1, 9))
            val memo = fixture.giveMe<CalendarMemo>(10)
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { calendarMemoRepository.get(account, dateRange) } returns flowOf(memo)

            When("유즈케이스를 구독하면") {
                val result = useCase(dateRange).first()

                Then("Repository 결과를 그대로 방출한다") {
                    result.shouldBeSuccess(memo)
                }
                Then("Repository 호출한다") {
                    verify(exactly = 1) { calendarMemoRepository.get(account, dateRange) }
                }
            }
        }
    }
}
