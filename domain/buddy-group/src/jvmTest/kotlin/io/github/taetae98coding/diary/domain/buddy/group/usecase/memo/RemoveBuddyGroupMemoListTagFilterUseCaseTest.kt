package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListTagFilterRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class RemoveBuddyGroupMemoListTagFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoListTagFilterRepository = mockk<BuddyGroupMemoListTagFilterRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = RemoveBuddyGroupMemoListTagFilterUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupMemoListTagFilterRepository = buddyGroupMemoListTagFilterRepository,
        )

        Given("로그인된 계정으로 메모 목록 태그 필터를 제거하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, tagId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupMemoListTagFilterRepository.upsert(
                            buddyGroupId = buddyGroupId,
                            tagId = tagId,
                            isFilter = false,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, tagId)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupMemoListTagFilterRepository.upsert(any(), any(), any())
                    }
                }
            }
        }
    }
}
