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
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageBuddyGroupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupRepository = mockk<BuddyGroupRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageBuddyGroupUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupRepository = buddyGroupRepository,
        )

        Given("로그인된 계정의 그룹 목록 페이징을 조회하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val paging = PagingData.notLoading<BuddyGroup>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.page(account) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("성공한다") {
                    result.shouldBeSuccess(paging)
                }
            }
        }

        Given("로그인하지 않은 경우") {
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
            }
        }
    }
}
