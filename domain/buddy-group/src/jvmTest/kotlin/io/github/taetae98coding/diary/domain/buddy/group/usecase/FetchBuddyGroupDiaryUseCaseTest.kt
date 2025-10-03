package io.github.taetae98coding.diary.domain.buddy.group.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupDiaryRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class FetchBuddyGroupDiaryUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<BuddyGroupDiaryRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = FetchBuddyGroupDiaryUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupDiaryRepository = repository,
        )

        Given("로그인 사용자로 그룹 일기를 가져오는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val groupId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(groupId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("레포지토리를 호출한다") {
                    coVerify(exactly = 1) { repository.fetch(account, groupId) }
                }
            }
        }

        Given("게스트로 그룹 일기를 가져오는 경우") {
            val groupId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(groupId)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("레포지토리를 호출하지 않는다") {
                    coVerify(exactly = 0) { repository.fetch(any(), any()) }
                }
            }
        }
    }
}


