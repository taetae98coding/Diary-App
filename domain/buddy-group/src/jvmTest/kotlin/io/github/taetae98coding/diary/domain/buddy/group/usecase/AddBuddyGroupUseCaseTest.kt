package io.github.taetae98coding.diary.domain.buddy.group.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
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
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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

        Given("제목이 빈 문자열인 경우") {
            val detail = BuddyGroupDetail(title = "", description = fixture.giveMeOne())
            val buddyIds = listOf(fixture.giveMeOne<Uuid>())

            When("유즈케이스를 실행하면") {
                val result = useCase(detail, buddyIds)

                Then("실패한다") {
                    result.shouldBeFailure<BuddyGroupTitleBlankException>()
                }
                Then("계정/레포지토리를 호출하지 않는다") {
                    verify(exactly = 0) { getAccountUseCase() }
                    coVerify(exactly = 0) { buddyGroupRepository.add(any(), any(), any()) }
                }
            }
        }

        Given("구성원이 비어있는 경우") {
            val detail = fixture.giveMeOne<BuddyGroupDetail>().copy(title = fixture.giveMeOne())
            val buddyIds = emptyList<Uuid>()

            When("유즈케이스를 실행하면") {
                val result = useCase(detail, buddyIds)

                Then("실패한다") {
                    result.shouldBeFailure<BuddyEmptyException>()
                }
                Then("계정/레포지토리를 호출하지 않는다") {
                    verify(exactly = 0) { getAccountUseCase() }
                    coVerify(exactly = 0) { buddyGroupRepository.add(any(), any(), any()) }
                }
            }
        }

        Given("로그인 사용자로 그룹을 추가하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val detail = fixture.giveMeOne<BuddyGroupDetail>().copy(title = fixture.giveMeOne())
            val buddyIds = listOf(fixture.giveMeOne<Uuid>())
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(detail, buddyIds)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("레포지토리를 호출한다") {
                    coVerify(exactly = 1) { buddyGroupRepository.add(account, detail, buddyIds) }
                }
            }
        }

        Given("게스트로 그룹을 추가하는 경우") {
            val detail = fixture.giveMeOne<BuddyGroupDetail>().copy(title = fixture.giveMeOne())
            val buddyIds = listOf(fixture.giveMeOne<Uuid>())
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(detail, buddyIds)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("레포지토리를 호출하지 않는다") {
                    coVerify(exactly = 0) { buddyGroupRepository.add(any(), any(), any()) }
                }
            }
        }
    }
}


