package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.MemoListTagFilterRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class HasMemoListFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<MemoListTagFilterRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = HasMemoListFilterUseCase(
            getAccountUseCase = getAccountUseCase,
            memoListTagFilterRepository = repository,
        )

        Given("사용자의 필터가 존재하는 경우") {
            val account = fixture.giveMeOne<Account>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { repository.hasFilter(account) } returns flowOf(true)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("true 를 방출한다") {
                    result.shouldBeSuccess(true)
                }
            }
        }
    }
}
