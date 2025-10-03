package io.github.taetae98coding.diary.domain.buddy.group.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
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
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
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

        Given("로그인 사용자로 그룹 제목이 비어있는 상세로 수정하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val groupId = fixture.giveMeOne<Uuid>()
            val currentTitle = fixture.giveMeOne<String>()
            val currentGroup = BuddyGroup(id = groupId, detail = BuddyGroupDetail(title = currentTitle, description = fixture.giveMeOne()))
            val detail = BuddyGroupDetail(title = "", description = fixture.giveMeOne())

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.get(groupId) } returns flowOf(currentGroup)

            When("유즈케이스를 실행하면") {
                val result = useCase(groupId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("빈 제목은 기존 제목으로 대체되어 업데이트된다") {
                    coVerify(exactly = 1) {
                        buddyGroupRepository.update(account, groupId, withArg { it.title shouldBe currentTitle })
                    }
                }
            }
        }

        Given("로그인 사용자로 유효한 제목으로 수정하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val groupId = fixture.giveMeOne<Uuid>()
            val newTitle = fixture.giveMeOne<String>()
            val currentGroup = fixture.giveMeOne<BuddyGroup>()
            val detail = BuddyGroupDetail(title = newTitle, description = fixture.giveMeOne())

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupRepository.get(groupId) } returns flowOf(currentGroup)

            When("유즈케이스를 실행하면") {
                val result = useCase(groupId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("새 제목으로 업데이트된다") {
                    coVerify(exactly = 1) {
                        buddyGroupRepository.update(account, groupId, withArg { it.title shouldBe newTitle })
                    }
                }
            }
        }

        Given("게스트가 수정하는 경우") {
            val groupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeOne<BuddyGroupDetail>()
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))
            every { buddyGroupRepository.get(groupId) } returns flowOf(null)

            When("유즈케이스를 실행하면") {
                val result = useCase(groupId, detail)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
            }
        }
    }
}


