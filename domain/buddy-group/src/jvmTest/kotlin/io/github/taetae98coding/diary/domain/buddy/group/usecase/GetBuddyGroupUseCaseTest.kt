package io.github.taetae98coding.diary.domain.buddy.group.usecase

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
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetBuddyGroupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupRepository = mockk<BuddyGroupRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetBuddyGroupUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupRepository = buddyGroupRepository,
        )

        Given("로그인된 계정으로 그룹이 존재하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val buddyGroup = fixture.giveMeOne<BuddyGroup>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.get(buddyGroupId) } returns flowOf(buddyGroup)

            When("유즈케이스를 구독하면") {
                val result = useCase(buddyGroupId).first()

                Then("그룹을 방출한다") {
                    result.shouldBeSuccess(buddyGroup)
                }
            }
        }

        Given("로그인된 계정으로 그룹이 존재하지 않는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.get(buddyGroupId) } returns flowOf(null)

            When("유즈케이스를 구독하면") {
                val result = useCase(buddyGroupId).first()

                Then("null을 방출한다") {
                    result.shouldBeSuccess(null)
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
