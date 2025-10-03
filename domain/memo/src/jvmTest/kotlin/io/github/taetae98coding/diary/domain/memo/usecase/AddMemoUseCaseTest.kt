package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.AccountMemoRepository
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class AddMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val instant = Instant.fromEpochMilliseconds(123L)
        val clock = mockk<Clock>(relaxed = true, relaxUnitFun = true) {
            every { now() } returns instant
        }
        val checkMemoDetailUseCase = mockk<CheckMemoDetailUseCase>(relaxed = true, relaxUnitFun = true)
        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val requestDiaryBackupUseCase = mockk<RequestDiaryBackupUseCase>(relaxed = true, relaxUnitFun = true)
        val accountMemoRepository = mockk<AccountMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = AddMemoUseCase(
            clock = clock,
            checkMemoDetailUseCase = checkMemoDetailUseCase,
            getAccountUseCase = getAccountUseCase,
            requestDiaryBackupUseCase = requestDiaryBackupUseCase,
            accountMemoRepository = accountMemoRepository,
        )

        val primaryTag: Uuid? = null
        val memoTagIds = setOf(fixture.giveMeOne<Uuid>())

        Given("유효한 상세로 메모를 추가하는 경우") {
            val detail = fixture.giveMeOne<MemoDetail>()
            val account = fixture.giveMeOne<Account>()

            coEvery { checkMemoDetailUseCase(detail) } returns Result.success(Unit)
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(detail, primaryTag, memoTagIds)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository 호출한다") {
                    coVerify(exactly = 1) {
                        accountMemoRepository.add(
                            account = account,
                            memo = withArg {
                                it.detail shouldBe detail
                                it.primaryTag shouldBe primaryTag
                                it.isFinished.shouldBeFalse()
                                it.isDeleted.shouldBeFalse()
                                it.updatedAt shouldBe instant.toEpochMilliseconds()
                                it.createdAt shouldBe instant.toEpochMilliseconds()
                            },
                            memoTagIds = memoTagIds,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("메모 상세가 유효하지 않은 경우") {
            val exception = Exception()
            val detail = fixture.giveMeOne<MemoDetail>()
            coEvery { checkMemoDetailUseCase(detail) } returns Result.failure(exception)

            When("유즈케이스를 실행하면") {
                val result = useCase(detail, primaryTag, memoTagIds)

                Then("실패한다") {
                    result.shouldBeFailure(exception)
                }
                Then("Repository 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        accountMemoRepository.add(any(), any(), any())
                    }
                }
            }
        }
    }
}
