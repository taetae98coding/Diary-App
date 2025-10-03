package io.github.taetae98coding.diary.domain.sync.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.sync.repository.DiaryBackupRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class RequestDiaryBackupUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val backupRepository = mockk<DiaryBackupRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = RequestDiaryBackupUseCase(
            getAccountUseCase = getAccountUseCase,
            backupRepository = backupRepository,
        )

        Given("로그인 사용자로 백업 요청하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase()

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("레포지토리를 호출한다") {
                    coVerify(exactly = 1) { backupRepository.requestBackup(account) }
                }

                clearAllMocks()
            }
        }

        Given("게스트로 백업 요청하는 경우") {
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase()

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("레포지토리를 호출하지 않는다") {
                    coVerify(exactly = 0) { backupRepository.requestBackup(any()) }
                }
            }
        }
    }
}
