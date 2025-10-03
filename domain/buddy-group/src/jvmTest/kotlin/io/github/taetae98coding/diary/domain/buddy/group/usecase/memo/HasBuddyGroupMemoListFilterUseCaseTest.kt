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
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class HasBuddyGroupMemoListFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoListTagFilterRepository = mockk<BuddyGroupMemoListTagFilterRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = HasBuddyGroupMemoListFilterUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupMemoListTagFilterRepository = buddyGroupMemoListTagFilterRepository,
        )

        Given("로그인된 계정으로 그룹의 메모 목록 필터 존재 여부를 조회하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val hasFilter = fixture.giveMeOne<Boolean>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupMemoListTagFilterRepository.hasFilter(buddyGroupId) } returns flowOf(hasFilter)

            When("유즈케이스를 구독하면") {
                val result = useCase(buddyGroupId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(hasFilter)
                }
                Then("Repository 호출한다") {
                    verify(exactly = 1) {
                        buddyGroupMemoListTagFilterRepository.hasFilter(buddyGroupId)
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
                        buddyGroupMemoListTagFilterRepository.hasFilter(any())
                    }
                }
            }
        }
    }
}
