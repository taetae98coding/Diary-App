package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarTagFilterRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class RemoveBuddyGroupMemoCalendarTagFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoCalendarTagFilterRepository = mockk<BuddyGroupMemoCalendarTagFilterRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = RemoveBuddyGroupMemoCalendarTagFilterUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupMemoCalendarTagFilterRepository = buddyGroupMemoCalendarTagFilterRepository,
        )

        Given("로그인된 계정으로 그룹의 태그 필터를 제거하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("UseCase를 실행하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    tagId = tagId,
                )

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupMemoCalendarTagFilterRepository.upsert(
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

            When("UseCase를 실행하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    tagId = tagId,
                )

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupMemoCalendarTagFilterRepository.upsert(any(), any(), any())
                    }
                }
            }
        }
    }
}
