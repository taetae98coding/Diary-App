package io.github.taetae98coding.diary.domain.buddy.group.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupBuddyRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageBuddyGroupBuddyUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupBuddyRepository = mockk<BuddyGroupBuddyRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageBuddyGroupBuddyUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupBuddyRepository = buddyGroupBuddyRepository,
        )

        Given("로그인된 계정의 그룹 친구 목록 페이징을 조회하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val paging = PagingData.notLoading<Buddy>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupBuddyRepository.page(account, buddyGroupId) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase(buddyGroupId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(paging)
                }
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 구독하면") {
                val result = useCase(buddyGroupId).first()

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
            }
        }
    }
}
