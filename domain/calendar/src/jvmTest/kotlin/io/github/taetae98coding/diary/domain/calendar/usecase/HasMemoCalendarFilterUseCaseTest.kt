package io.github.taetae98coding.diary.domain.calendar.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.calendar.repository.MemoCalendarTagFilterRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class HasMemoCalendarFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val memoCalendarTagFilterRepository = mockk<MemoCalendarTagFilterRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = HasMemoCalendarFilterUseCase(
            getAccountUseCase = getAccountUseCase,
            memoCalendarTagFilterTagRepository = memoCalendarTagFilterRepository,
        )

        Given("사용자의 캘린더 메모 필터 존재 여부를 조회하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val hasFilter = fixture.giveMeOne<Boolean>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { memoCalendarTagFilterRepository.hasFilter(account) } returns flowOf(hasFilter)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("성공한다") { result.shouldBeSuccess(hasFilter) }
                Then("Repository 호출한다") {
                    verify(exactly = 1) { memoCalendarTagFilterRepository.hasFilter(account) }
                }
            }
        }
    }
}
