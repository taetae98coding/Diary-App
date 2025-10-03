package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.MemoRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class FetchMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val memoRepository = mockk<MemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = FetchMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            memoRepository = memoRepository,
        )

        Given("로그인 사용자로 메모를 가져오는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val memoId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository가 호출된다") {
                    coVerify(exactly = 1) { memoRepository.fetch(account, memoId) }
                }
            }
        }

        Given("게스트로 메모를 가져오는 경우") {
            val memoId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
            }
        }
    }
}
