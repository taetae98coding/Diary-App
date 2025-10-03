package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoTagRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class UnselectMemoTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val getMemoUseCase = mockk<GetMemoUseCase>(relaxed = true, relaxUnitFun = true)
        val requestDiaryBackupUseCase = mockk<RequestDiaryBackupUseCase>(relaxed = true, relaxUnitFun = true)
        val accountMemoTagRepository = mockk<AccountMemoTagRepository>(relaxed = true, relaxUnitFun = true)
        val accountMemoRepository = mockk<AccountMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = UnselectMemoTagUseCase(
            getAccountUseCase = getAccountUseCase,
            getMemoUseCase = getMemoUseCase,
            requestDiaryBackupUseCase = requestDiaryBackupUseCase,
            accountMemoTagRepository = accountMemoTagRepository,
            accountMemoRepository = accountMemoRepository,
        )

        Given("대표 태그가 아닌 태그를 해제하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val memoId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()
            val memo = fixture.giveMeOne<Memo>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getMemoUseCase(memoId) } returns flowOf(Result.success(memo))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId, tagId)

                Then("성공한다") { result.shouldBeSuccess() }
                Then("메모 태그만 해제한다") {
                    coVerify(exactly = 1) { accountMemoTagRepository.updateMemoTag(account, memoId, tagId, false) }
                    coVerify(exactly = 0) { accountMemoRepository.updatePrimaryTag(any(), any(), any()) }
                }
                clearAllMocks()
            }
        }

        Given("대표 태그를 해제하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val memoId = fixture.giveMeOne<Uuid>()
            val tagId = fixture.giveMeOne<Uuid>()
            val memo = fixture.giveMeKotlinBuilder<Memo>()
                .set(Memo::primaryTag, tagId)
                .sample()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getMemoUseCase(memoId) } returns flowOf(Result.success(memo))

            When("유즈케이스를 실행하면") {
                val result = useCase(memoId, tagId)

                Then("성공한다") { result.shouldBeSuccess() }
                Then("메모 태그 해제와 대표 태그 제거를 모두 수행한다") {
                    coVerify(exactly = 1) { accountMemoTagRepository.updateMemoTag(account, memoId, tagId, false) }
                    coVerify(exactly = 1) { accountMemoRepository.updatePrimaryTag(account, memoId, null) }
                }
            }
        }
    }
}
