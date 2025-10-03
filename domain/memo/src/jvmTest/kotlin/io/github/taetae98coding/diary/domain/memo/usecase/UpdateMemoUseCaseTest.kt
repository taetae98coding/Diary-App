package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.exception.MemoNotFoundException
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class UpdateMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val requestDiaryBackupUseCase = mockk<RequestDiaryBackupUseCase>(relaxed = true, relaxUnitFun = true)
        val getMemoUseCase = mockk<GetMemoUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<AccountMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = UpdateMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            requestDiaryBackupUseCase = requestDiaryBackupUseCase,
            getMemoUseCase = getMemoUseCase,
            accountMemoRepository = repository,
        )

        Given("메모가 존재하지 않는 경우") {
            val memoId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeOne<MemoDetail>()
            val account = fixture.giveMeOne<Account>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getMemoUseCase(memoId) } returns flowOf(Result.success(null))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId, detail)

                Then("실패한다") {
                    result.shouldBeFailure<MemoNotFoundException>()
                }
                Then("레포지토리를 호출하지 않는다") {
                    coVerify(exactly = 0) { repository.update(any(), any(), any()) }
                }
            }
        }

        Given("메모가 존재하고 제목이 빈 문자열인 경우") {
            val memoId = fixture.giveMeOne<Uuid>()
            val memo = fixture.giveMeOne<Memo>()
            val account = fixture.giveMeOne<Account>()
            val detail = fixture.giveMeOne<MemoDetail>().copy(title = "")
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getMemoUseCase(memoId) } returns flowOf(Result.success(memo))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("빈 제목은 기존 제목으로 대체되어 업데이트된다") {
                    coVerify(exactly = 1) {
                        repository.update(account, memoId, detail.copy(title = memo.detail.title))
                    }
                }
            }
        }

        Given("메모가 존재하고 제목이 유효한 경우") {
            val memoId = fixture.giveMeOne<Uuid>()
            val existed = fixture.giveMeOne<Memo>()
            val account = fixture.giveMeOne<Account>()
            val detail = fixture.giveMeOne<MemoDetail>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getMemoUseCase(memoId) } returns flowOf(Result.success(existed))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("입력 제목 그대로 업데이트된다") {
                    coVerify(exactly = 1) {
                        repository.update(account, memoId, detail)
                    }
                }
            }
        }
    }
}
