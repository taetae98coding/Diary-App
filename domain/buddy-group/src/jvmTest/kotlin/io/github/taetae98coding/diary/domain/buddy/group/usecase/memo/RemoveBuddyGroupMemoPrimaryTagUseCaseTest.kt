package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class RemoveBuddyGroupMemoPrimaryTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoRepository = mockk<BuddyGroupMemoRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = RemoveBuddyGroupMemoPrimaryTagUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupMemoRepository = buddyGroupMemoRepository,
        )

        Given("로그인된 계정으로 메모 주요 태그를 제거하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val memoId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, memoId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupMemoRepository.updatePrimaryTag(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            memoId = memoId,
                            tagId = null,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val memoId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, memoId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupMemoRepository.updatePrimaryTag(any(), any(), any(), any())
                    }
                }
            }
        }
    }
}
