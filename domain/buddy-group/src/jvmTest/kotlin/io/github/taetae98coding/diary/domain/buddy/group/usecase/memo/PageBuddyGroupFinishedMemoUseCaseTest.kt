package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupFinishedMemoRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageBuddyGroupFinishedMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupFinishedMemoRepository = mockk<BuddyGroupFinishedMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageBuddyGroupFinishedMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupFinishedMemoRepository = buddyGroupFinishedMemoRepository,
        )

        Given("로그인된 계정으로 그룹의 완료된 메모를 페이징하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val pagingData = PagingData.empty<Memo>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupFinishedMemoRepository.page(account, buddyGroupId) } returns flowOf(pagingData)

            When("유즈케이스를 구독하면") {
                val result = useCase(buddyGroupId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(pagingData)
                }
                Then("Repository 호출한다") {
                    verify(exactly = 1) {
                        buddyGroupFinishedMemoRepository.page(account, buddyGroupId)
                    }
                }
                clearAllMocks()
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
                Then("Repository 호출하지 않는다") {
                    verify(exactly = 0) {
                        buddyGroupFinishedMemoRepository.page(any(), any())
                    }
                }
            }
        }
    }
}
