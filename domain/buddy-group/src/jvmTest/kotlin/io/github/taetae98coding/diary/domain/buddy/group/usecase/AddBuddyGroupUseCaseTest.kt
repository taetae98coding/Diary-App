package io.github.taetae98coding.diary.domain.buddy.group.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroupDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyEmptyException
import io.github.taetae98coding.diary.domain.buddy.group.exception.BuddyGroupTitleBlankException
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

class AddBuddyGroupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupRepository = mockk<BuddyGroupRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = AddBuddyGroupUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupRepository = buddyGroupRepository,
        )

        Given("유효한 그룹 상세와 친구 목록으로 추가하는 경우") {
            val detail = fixture.giveMeOne<BuddyGroupDetail>()
            val account = fixture.giveMeOne<Account.User>()
            val buddyIds = fixture.giveMe<Uuid>(5)

            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(detail = detail, buddyIds = buddyIds)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupRepository.add(
                            account = account,
                            detail = detail,
                            buddyIds = buddyIds,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("그룹 제목이 공백인 경우") {
            val detail = fixture.giveMeKotlinBuilder<BuddyGroupDetail>()
                .set(BuddyGroupDetail::title, "")
                .sample()
            val buddyIds = fixture.giveMe<Uuid>(5)

            When("유즈케이스를 실행하면") {
                val result = useCase(detail = detail, buddyIds = buddyIds)

                Then("실패한다") {
                    result.shouldBeFailure<BuddyGroupTitleBlankException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupRepository.add(any(), any(), any())
                    }
                }
            }
        }

        Given("친구 목록이 비어있는 경우") {
            val detail = fixture.giveMeOne<BuddyGroupDetail>()
            val buddyIds = emptySet<Uuid>()

            When("유즈케이스를 실행하면") {
                val result = useCase(detail = detail, buddyIds = buddyIds)

                Then("실패한다") {
                    result.shouldBeFailure<BuddyEmptyException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupRepository.add(any(), any(), any())
                    }
                }
            }
        }

        Given("로그인하지 않은 경우") {
            val detail = fixture.giveMeOne<BuddyGroupDetail>()
            val buddyIds = setOf(fixture.giveMeOne<Uuid>())

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(detail = detail, buddyIds = buddyIds)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupRepository.add(any(), any(), any())
                    }
                }
            }
        }
    }
}
