package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class RestartMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val requestDiaryBackupUseCase = mockk<RequestDiaryBackupUseCase>(relaxed = true, relaxUnitFun = true)
        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val accountMemoRepository = mockk<AccountMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = RestartMemoUseCase(
            requestDiaryBackupUseCase = requestDiaryBackupUseCase,
            getAccountUseCase = getAccountUseCase,
            accountMemoRepository = accountMemoRepository,
        )

        Given("로그인 사용자로 메모 완료를 되돌리는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val memoId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId)

                Then("성공한다") { result.shouldBeSuccess() }
                Then("Repository와 백업 유즈케이스가 호출된다") {
                    coVerify(exactly = 1) { accountMemoRepository.updateFinished(account, memoId, false) }
                    coVerify(exactly = 1) { requestDiaryBackupUseCase() }
                }
                clearAllMocks()
            }
        }

        Given("게스트 사용자로 메모 완료를 되돌리는 경우") {
            val account = Account.Guest
            val memoId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId)

                Then("성공한다") { result.shouldBeSuccess() }
                Then("Repository는 호출되지만 백업 유즈케이스는 호출되지 않는다") {
                    coVerify(exactly = 1) { accountMemoRepository.updateFinished(account, memoId, false) }
                    coVerify(exactly = 0) { requestDiaryBackupUseCase() }
                }
            }
        }
    }
}
