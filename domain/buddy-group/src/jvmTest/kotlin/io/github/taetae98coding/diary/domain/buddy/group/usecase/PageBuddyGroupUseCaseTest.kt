package io.github.taetae98coding.diary.domain.buddy.group.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageBuddyGroupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<BuddyGroupRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageBuddyGroupUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupRepository = repository,
        )

        Given("게스트가 페이지를 구독하는 경우") {
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("레포지토리를 호출하지 않는다") {
                    verify(exactly = 0) { repository.page(any()) }
                }
            }
        }

        Given("로그인 사용자가 페이지를 구독하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val paging = PagingData.empty<BuddyGroup>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { repository.page(account) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
            }
        }
    }
}


