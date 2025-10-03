package io.github.taetae98coding.diary.domain.buddy.group.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class UpdateBuddyGroupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupRepository = mockk<BuddyGroupRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = UpdateBuddyGroupUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupRepository = buddyGroupRepository,
        )

        Given("로그인된 계정이 그룹을 수정하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeOne<BuddyGroupDetail>()
            val buddyGroup = fixture.giveMeOne<BuddyGroup>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.get(buddyGroupId) } returns flowOf(buddyGroup)

            When("유즈케이스를 실행하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    detail = detail,
                )

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupRepository.update(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            detail = detail,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("제목이 공백인 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<BuddyGroupDetail>()
                .set(BuddyGroupDetail::title, "")
                .sample()
            val buddyGroup = fixture.giveMeOne<BuddyGroup>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.get(buddyGroupId) } returns flowOf(buddyGroup)

            When("유즈케이스를 실행하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    detail = detail,
                )

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("기존 제목으로 수정한다") {
                    coVerify(exactly = 1) {
                        buddyGroupRepository.update(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            detail = detail.copy(title = buddyGroup.detail.title),
                        )
                    }
                }
                clearAllMocks()
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeOne<BuddyGroupDetail>()

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    detail = detail,
                )

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupRepository.update(any(), any(), any())
                    }
                }
            }
        }
    }
}
